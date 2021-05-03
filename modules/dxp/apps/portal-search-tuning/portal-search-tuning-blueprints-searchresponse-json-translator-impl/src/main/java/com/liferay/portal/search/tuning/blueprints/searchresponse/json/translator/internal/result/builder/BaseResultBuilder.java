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

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.result.builder;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.tuning.blueprints.engine.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.constants.ResponseAttributeKeys;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.util.ResultUtil;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.result.ResultBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseResultBuilder implements ResultBuilder {

	public static final DateFormat INDEX_DATE_FORMAT = new SimpleDateFormat(
		"yyyyMMddHHmmss");

	@Override
	public String getAuthor(
			Document document, BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		return document.getString(Field.USER_NAME);
	}

	@Override
	public String getCreateDate(
			Document document, BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		return _formatDate(
			document, Field.CREATE_DATE, blueprintsAttributes.getLocale());
	}

	public String getModificationDate(
			Document document, BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		return _formatDate(
			document, Field.MODIFIED_DATE, blueprintsAttributes.getLocale());
	}

	@Override
	public String getSummary(
			Document document, BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		String description = getStringFieldContent(
			document, Field.CONTENT, blueprintsAttributes.getLocale());

		return ResultUtil.stripHTML(
			description, getSummaryMaxLength(blueprintsAttributes));
	}

	@Override
	public String getThumbnail(
			Document document, BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		return StringPool.BLANK;
	}

	@Override
	public String getTitle(
			Document document, BlueprintsAttributes blueprintsAttributes)
		throws Exception {

		String title = getStringFieldContent(
			document, Field.TITLE, blueprintsAttributes.getLocale());

		return ResultUtil.stripHTML(title, -1);
	}

	@Override
	public String getType(Document document) {
		return document.getString(Field.ENTRY_CLASS_NAME);
	}

	protected String buildLocalizedSnippetFieldName(
		String field, Locale locale) {

		StringBundler sb = new StringBundler(5);

		sb.append(Field.SNIPPET);
		sb.append(StringPool.UNDERLINE);
		sb.append(field);
		sb.append(StringPool.UNDERLINE);
		sb.append(locale.toString());

		return sb.toString();
	}

	protected AssetRenderer<?> getAssetRenderer(Document document)
		throws NumberFormatException, PortalException {

		String entryClassName = document.getString(Field.ENTRY_CLASS_NAME);
		long entryClassPK = Long.valueOf(
			document.getString(Field.ENTRY_CLASS_PK));

		return getAssetRenderer(entryClassName, entryClassPK);
	}

	protected AssetRenderer<?> getAssetRenderer(
			String entryClassName, long entryClassPK)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				entryClassName);

		return assetRendererFactory.getAssetRenderer(entryClassPK);
	}

	protected Indexer<Object> getIndexer(String className) {
		return IndexerRegistryUtil.getIndexer(className);
	}

	protected PortletRequest getPortletRequest(
			BlueprintsAttributes blueprintsAttributes)
		throws ClassCastException {

		Optional<Object> portletRequestOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.PORTLET_REQUEST);

		if (!portletRequestOptional.isPresent()) {
			return null;
		}

		return (PortletRequest)portletRequestOptional.get();
	}

	protected PortletResponse getPortletResponse(
			BlueprintsAttributes blueprintsAttributes)
		throws ClassCastException {

		Optional<Object> portletResponseOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.PORTLET_RESPONSE);

		if (!portletResponseOptional.isPresent()) {
			return null;
		}

		return (PortletResponse)portletResponseOptional.get();
	}

	protected String getStringFieldContent(
		Document document, String field, Locale locale) {

		String fieldName = null;
		String value = null;

		if (Validator.isNull(value)) {
			fieldName = _buildLocalizedFieldName(field, locale);

			value = document.getString(fieldName);
		}

		if (Validator.isNull(value)) {
			fieldName = _buildLocalizedFieldName2(field, locale);

			value = document.getString(fieldName);
		}

		if (Validator.isNull(value)) {
			value = document.getString(field);
		}

		return value;
	}

	protected int getSummaryMaxLength(
		BlueprintsAttributes blueprintsAttributes) {

		Optional<Object> descriptionMaxLengthOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.SUMMARY_MAX_LENGTH);

		return GetterUtil.getInteger(descriptionMaxLengthOptional.orElse(700));
	}

	protected boolean isViewInContext(
		BlueprintsAttributes blueprintsAttributes) {

		Optional<Object> viewResultsInContextOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.VIEW_RESULT_IN_CONTEXT);

		return GetterUtil.getBoolean(viewResultsInContextOptional.orElse(true));
	}

	private String _buildLocalizedFieldName(String field, Locale locale) {
		StringBundler sb = new StringBundler(3);

		sb.append(field);
		sb.append(StringPool.UNDERLINE);
		sb.append(locale.toString());

		return sb.toString();
	}

	private String _buildLocalizedFieldName2(String field, Locale locale) {
		StringBundler sb = new StringBundler(5);

		sb.append("localized");
		sb.append(StringPool.UNDERLINE);
		sb.append(field);
		sb.append(StringPool.UNDERLINE);
		sb.append(locale.toString());

		return sb.toString();
	}

	private String _formatDate(Document document, String field, Locale locale) {
		String dateString = "";

		try {
			String modified = document.getDate(Field.CREATE_DATE);

			if (!Validator.isBlank(modified)) {
				Date lastModified = INDEX_DATE_FORMAT.parse(modified);

				DateFormat dateFormat = DateFormat.getDateTimeInstance(
					DateFormat.SHORT, DateFormat.SHORT, locale);

				dateString = dateFormat.format(lastModified);
			}
		}
		catch (Exception exception) {
			StringBundler sb = new StringBundler(4);

			sb.append("Error in formatting ");
			sb.append(field);
			sb.append(" for entryClassPK ");
			sb.append(document.getDate(Field.ENTRY_CLASS_PK));

			_log.error(sb.toString(), exception);
		}

		return dateString;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseResultBuilder.class);

}