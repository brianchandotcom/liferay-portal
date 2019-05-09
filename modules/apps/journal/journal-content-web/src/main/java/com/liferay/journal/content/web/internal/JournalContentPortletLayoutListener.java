/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.content.web.internal;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletLayoutListenerException;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.layoutconfiguration.util.xml.PortletLogic;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
	service = PortletLayoutListener.class
)
public class JournalContentPortletLayoutListener
	implements PortletLayoutListener {

	@Override
	public void onAddToLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat("Add ", portletId, " to layout ", plid));
		}

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			JournalArticle article = _getArticle(layout, portletId);

			if (article == null) {
				return;
			}

			_addAssetEntryUsage(layout, portletId, article);

			_journalContentSearchLocalService.updateContentSearch(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, article.getArticleId(), true);
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	@Override
	public void onMoveInLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat("Move ", portletId, " from in ", plid));
		}
	}

	@Override
	public void onRemoveFromLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Remove ", portletId, " from layout ", plid));
		}

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			JournalArticle article = _getArticle(layout, portletId);

			if (article == null) {
				return;
			}

			_assetEntryUsageLocalService.deleteAssetEntryUsages(
				_portal.getClassNameId(Portlet.class), portletId, plid);

			_journalContentSearchLocalService.deleteArticleContentSearch(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, article.getArticleId());

			String[] runtimePortletIds = getRuntimePortletIds(
				layout.getCompanyId(), layout.getGroupId(),
				article.getArticleId());

			if (runtimePortletIds.length > 0) {
				_portletLocalService.deletePortlets(
					layout.getCompanyId(), runtimePortletIds, layout.getPlid());
			}
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	@Override
	public void onSetup(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Setup ", portletId, " from layout ", plid));
		}

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			_assetEntryUsageLocalService.deleteAssetEntryUsages(
				_portal.getClassNameId(Portlet.class), portletId, plid);

			_journalContentSearchLocalService.deleteArticleContentSearch(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId);

			JournalArticle article = _getArticle(layout, portletId);

			if (article != null) {
				_addAssetEntryUsage(layout, portletId, article);

				_journalContentSearchLocalService.updateContentSearch(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(), portletId, article.getArticleId(),
					true);
			}
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	@Override
	public void updatePropertiesOnRemoveFromLayout(
			String portletId, UnicodeProperties typeSettingsProperties)
		throws PortletLayoutListenerException {
	}

	protected String getRuntimePortletId(String xml) throws Exception {
		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		String portletName = rootElement.attributeValue("name");
		String instanceId = rootElement.attributeValue("instance");

		return PortletIdCodec.encode(portletName, 0, instanceId);
	}

	protected String[] getRuntimePortletIds(
			long companyId, long scopeGroupId, String articleId)
		throws Exception {

		JournalArticle article =
			_journalArticleLocalService.fetchDisplayArticle(
				scopeGroupId, articleId);

		if (article == null) {
			Group group = _groupLocalService.fetchGroup(companyId);

			if (group == null) {
				return new String[0];
			}

			article = _journalArticleLocalService.fetchDisplayArticle(
				group.getGroupId(), articleId);

			if (article == null) {
				return new String[0];
			}
		}

		Set<String> portletIds = getRuntimePortletIds(article.getContent());

		if (Validator.isNotNull(article.getDDMTemplateKey())) {
			DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
				scopeGroupId, _portal.getClassNameId(DDMStructure.class),
				article.getDDMTemplateKey(), true);

			if (ddmTemplate != null) {
				portletIds.addAll(
					getRuntimePortletIds(ddmTemplate.getScript()));
			}
		}

		return portletIds.toArray(new String[0]);
	}

	protected Set<String> getRuntimePortletIds(String content)
		throws Exception {

		Set<String> portletIds = new LinkedHashSet<>();

		for (int index = 0;;) {
			index = content.indexOf(PortletLogic.OPEN_TAG, index);

			if (index == -1) {
				break;
			}

			int close1 = content.indexOf(PortletLogic.CLOSE_1_TAG, index);
			int close2 = content.indexOf(PortletLogic.CLOSE_2_TAG, index);

			int closeIndex = -1;

			if ((close2 == -1) || ((close1 != -1) && (close1 < close2))) {
				closeIndex = close1 + PortletLogic.CLOSE_1_TAG.length();
			}
			else {
				closeIndex = close2 + PortletLogic.CLOSE_2_TAG.length();
			}

			if (closeIndex == -1) {
				break;
			}

			portletIds.add(
				getRuntimePortletId(content.substring(index, closeIndex)));

			index = closeIndex;
		}

		return portletIds;
	}

	private void _addAssetEntryUsage(
		Layout layout, String portletId, JournalArticle article) {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			_portal.getClassNameId(JournalArticle.class),
			article.getResourcePrimKey());

		if (assetEntry == null) {
			return;
		}

		AssetEntryUsage assetEntryUsage =
			_assetEntryUsageLocalService.fetchAssetEntryUsage(
				assetEntry.getEntryId(), _portal.getClassNameId(Portlet.class),
				portletId, layout.getPlid());

		if (assetEntryUsage != null) {
			return;
		}

		_assetEntryUsageLocalService.addAssetEntryUsage(
			layout.getGroupId(), assetEntry.getEntryId(),
			_portal.getClassNameId(Portlet.class), portletId, layout.getPlid(),
			ServiceContextThreadLocal.getServiceContext());
	}

	private JournalArticle _getArticle(Layout layout, String portletId) {
		PortletPreferences portletPreferences = null;

		if (layout.isPortletEmbedded(portletId, layout.getGroupId())) {
			portletPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout.getCompanyId(), layout.getGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
					PortletKeys.PREFS_PLID_SHARED, portletId, null);
		}
		else {
			portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(
				layout, portletId, StringPool.BLANK);
		}

		if (portletPreferences == null) {
			return null;
		}

		long groupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", null));
		String articleId = portletPreferences.getValue("articleId", null);

		return _journalArticleLocalService.fetchArticle(groupId, articleId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentPortletLayoutListener.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalContentSearchLocalService _journalContentSearchLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}