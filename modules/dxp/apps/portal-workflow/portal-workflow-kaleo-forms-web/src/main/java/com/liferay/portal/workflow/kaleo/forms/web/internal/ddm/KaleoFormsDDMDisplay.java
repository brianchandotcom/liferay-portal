/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.web.internal.ddm;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMNavigationHelper;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "javax.portlet.name=" + KaleoFormsPortletKeys.KALEO_FORMS_ADMIN,
	service = DDMDisplay.class
)
public class KaleoFormsDDMDisplay extends BaseDDMDisplay {

	@Override
	public String getEditTemplateBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK, long resourceClassNameId, String portletResource)
		throws Exception {

		DDMNavigationHelper ddmNavigationHelper = getDDMNavigationHelper();

		if (ddmNavigationHelper.isNavigationStartsOnEditTemplate(
				liferayPortletRequest)) {

			return StringPool.BLANK;
		}

		if (ddmNavigationHelper.isNavigationStartsOnSelectTemplate(
				liferayPortletRequest)) {

			return _getSelectTemplateURL(
				liferayPortletRequest, classNameId, classPK,
				resourceClassNameId);
		}

		return super.getEditTemplateBackURL(
			liferayPortletRequest, liferayPortletResponse, classNameId, classPK,
			resourceClassNameId, portletResource);
	}

	@Override
	public String getEditTemplateTitle(
		DDMStructure structure, DDMTemplate template, Locale locale) {

		if ((structure != null) && (template == null)) {
			return LanguageUtil.format(
				getResourceBundle(locale), "new-form-for-field-set-x",
				structure.getName(locale), false);
		}

		return super.getEditTemplateTitle(structure, template, locale);
	}

	@Override
	public String getPortletId() {
		return KaleoFormsPortletKeys.KALEO_FORMS_ADMIN;
	}

	@Override
	public String getStorageType() {
		return StorageType.DEFAULT.toString();
	}

	@Override
	public String getStructureName(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "field-set");
	}

	@Override
	public String getStructureType() {
		return KaleoProcess.class.getName();
	}

	@Override
	public String getTemplateType() {
		return DDMTemplateConstants.TEMPLATE_TYPE_FORM;
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "field-sets");
	}

	@Override
	public String getViewTemplatesBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK)
		throws Exception {

		DDMNavigationHelper ddmNavigationHelper = getDDMNavigationHelper();

		if (ddmNavigationHelper.isNavigationStartsOnEditStructure(
				liferayPortletRequest)) {

			return StringPool.BLANK;
		}

		return super.getViewTemplatesBackURL(
			liferayPortletRequest, liferayPortletResponse, classPK);
	}

	@Override
	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, boolean search,
		Locale locale) {

		if (structure != null) {
			return LanguageUtil.format(
				getResourceBundle(locale), "forms-for-field-set-x",
				structure.getName(locale), false);
		}

		return getDefaultViewTemplateTitle(locale);
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return true;
	}

	@Override
	protected String getDefaultViewTemplateTitle(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "forms");
	}

	private String _getSelectTemplateURL(
			LiferayPortletRequest liferayPortletRequest, long classNameId,
			long classPK, long resourceClassNameId)
		throws Exception {

		String portletId = PortletProviderUtil.getPortletId(
			DDMStructure.class.getName(), PortletProvider.Action.VIEW);

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				liferayPortletRequest, portletId, PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/select_template.jsp"
		).setParameter(
			"classNameId", classNameId
		).setParameter(
			"classPK", classPK
		).setParameter(
			"eventName", "selectStructure"
		).buildPortletURL();

		String mode = ParamUtil.getString(liferayPortletRequest, "mode");

		portletURL.setParameter("mode", mode);

		portletURL.setParameter(
			"resourceClassNameId", String.valueOf(resourceClassNameId));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	@Reference
	private Portal _portal;

}