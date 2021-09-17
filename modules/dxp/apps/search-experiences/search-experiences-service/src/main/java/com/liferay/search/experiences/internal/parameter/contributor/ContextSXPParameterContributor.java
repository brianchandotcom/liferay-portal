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

package com.liferay.search.experiences.internal.parameter.contributor;

import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.search.experiences.attributes.SXPAttributes;
import com.liferay.search.experiences.internal.attributes.util.SXPAttributeValueHelper;
import com.liferay.search.experiences.internal.problem.ProblemUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.parameter.BooleanParameter;
import com.liferay.search.experiences.parameter.LongParameter;
import com.liferay.search.experiences.parameter.SXPParameterContributor;
import com.liferay.search.experiences.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.parameter.SXPParameterDefinition;
import com.liferay.search.experiences.parameter.StringParameter;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=context",
	service = SXPParameterContributor.class
)
public class ContextSXPParameterContributor implements SXPParameterContributor {

	@Override
	public void contribute(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes, SXPBlueprint sxpBlueprint) {

		_addCompanyId(sxpParameterDataBuilder, sxpAttributes);

		_addCTCollectionParameter(sxpParameterDataBuilder);

		_addGroupParameters(sxpParameterDataBuilder, sxpAttributes);

		_addLanguage(sxpParameterDataBuilder, sxpAttributes);

		_addLayoutNameCurrentValue(sxpParameterDataBuilder, sxpAttributes);

		_addPlid(sxpParameterDataBuilder, sxpAttributes);
	}

	@Override
	public String getCategoryNameKey() {
		return "context";
	}

	@Override
	public List<SXPParameterDefinition> getParameterDefinitions() {
		return ListUtil.fromArray(
			new SXPParameterDefinition(
				_getTemplateVariableName("company_id"),
				LongParameter.class.getName(),
				"core.parameter.context.company-id"),
			new SXPParameterDefinition(
				_getTemplateVariableName("ct_collection_id"),
				LongParameter.class.getName(),
				"core.parameter.context.ct-collection-id"),
			new SXPParameterDefinition(
				_getTemplateVariableName("scope_group_id"),
				LongParameter.class.getName(),
				"core.parameter.context.scope-group-id"),
			new SXPParameterDefinition(
				_getTemplateVariableName("layout_locale_name"),
				StringParameter.class.getName(),
				"core.parameter.context.layout-locale-name"),
			new SXPParameterDefinition(
				_getTemplateVariableName("plid"), LongParameter.class.getName(),
				"core.parameter.context.plid"),
			new SXPParameterDefinition(
				_getTemplateVariableName("language"),
				StringParameter.class.getName(),
				"core.parameter.context.language"),
			new SXPParameterDefinition(
				_getTemplateVariableName("language_id"),
				StringParameter.class.getName(),
				"core.parameter.context.language-id"));
	}

	private void _addCompanyId(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes) {

		sxpParameterDataBuilder.addParameter(
			new LongParameter(
				"company_id", _getTemplateVariableName("company_id"),
				sxpAttributes.getCompanyId()));
	}

	private void _addCTCollectionParameter(
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		sxpParameterDataBuilder.addParameter(
			new LongParameter(
				"ct_collection_id",
				_getTemplateVariableName("ct_collection_id"),
				CTCollectionThreadLocal.getCTCollectionId()));
	}

	private void _addGroupParameters(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes) {

		Optional<Long> optional = _sxpAttributeValuesHelper.getLongOptional(
			sxpAttributes, "scope_group_id");

		if (!optional.isPresent()) {
			return;
		}

		long scopeGroupId = optional.get();

		sxpParameterDataBuilder.addParameter(
			new LongParameter(
				"scope_group_id", _getTemplateVariableName("scope_group_id"),
				scopeGroupId));

		Group group = _getGroup(scopeGroupId);

		if (group == null) {
			return;
		}

		sxpParameterDataBuilder.addParameter(
			new BooleanParameter(
				"is_staging_group",
				_getTemplateVariableName("is_staging_group"),
				group.isStagingGroup()));
	}

	private void _addLanguage(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes) {

		Locale locale = sxpAttributes.getLocale();

		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"language_id", _getTemplateVariableName("language_id"),
				"_" + _language.getLanguageId(locale)));

		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"language", _getTemplateVariableName("language"),
				locale.getLanguage()));
	}

	private void _addLayoutNameCurrentValue(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes) {

		Optional<Long> optional = _sxpAttributeValuesHelper.getLongOptional(
			sxpAttributes, "plid");

		if (!optional.isPresent()) {
			return;
		}

		long plid = optional.get();

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			sxpParameterDataBuilder.addParameter(
				new StringParameter(
					"layout_locale_name",
					_getTemplateVariableName("layout_locale_name"),
					layout.getName(sxpAttributes.getLocale(), true)));
		}
		catch (PortalException portalException) {
			ProblemUtil.addError(
				getClass().getName(), "layout-not-found", null, null,
				GetterUtil.getString(plid), portalException);
		}
	}

	private void _addPlid(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes) {

		Optional<Long> optional = _sxpAttributeValuesHelper.getLongOptional(
			sxpAttributes, "plid");

		if (!optional.isPresent()) {
			return;
		}

		sxpParameterDataBuilder.addParameter(
			new LongParameter(
				"plid", _getTemplateVariableName("plid"), optional.get()));
	}

	private Group _getGroup(long groupId) {
		try {
			return _groupLocalService.getGroup(groupId);
		}
		catch (PortalException portalException) {
			ProblemUtil.addError(
				getClass().getName(), "group-not-found", null, null,
				GetterUtil.getString(groupId), portalException);
		}

		return null;
	}

	private String _getTemplateVariableName(String key) {
		StringBundler sb = new StringBundler(3);

		sb.append("${context.");
		sb.append(key);
		sb.append("}");

		return sb.toString();
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private SXPAttributeValueHelper _sxpAttributeValuesHelper;

}