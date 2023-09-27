/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("ObjectDefinition")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ObjectDefinition")
public class ObjectDefinition implements Serializable {

	public static ObjectDefinition toDTO(String json) {
		return ObjectMapperUtil.readValue(ObjectDefinition.class, json);
	}

	public static ObjectDefinition unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ObjectDefinition.class, json);
	}

	@Schema
	public Boolean getAccountEntryRestricted() {
		return accountEntryRestricted;
	}

	public void setAccountEntryRestricted(Boolean accountEntryRestricted) {
		this.accountEntryRestricted = accountEntryRestricted;
	}

	@JsonIgnore
	public void setAccountEntryRestricted(
		UnsafeSupplier<Boolean, Exception>
			accountEntryRestrictedUnsafeSupplier) {

		try {
			accountEntryRestricted = accountEntryRestrictedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean accountEntryRestricted;

	@Schema
	public String getAccountEntryRestrictedObjectFieldName() {
		return accountEntryRestrictedObjectFieldName;
	}

	public void setAccountEntryRestrictedObjectFieldName(
		String accountEntryRestrictedObjectFieldName) {

		this.accountEntryRestrictedObjectFieldName =
			accountEntryRestrictedObjectFieldName;
	}

	@JsonIgnore
	public void setAccountEntryRestrictedObjectFieldName(
		UnsafeSupplier<String, Exception>
			accountEntryRestrictedObjectFieldNameUnsafeSupplier) {

		try {
			accountEntryRestrictedObjectFieldName =
				accountEntryRestrictedObjectFieldNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String accountEntryRestrictedObjectFieldName;

	@Schema
	@Valid
	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	@JsonIgnore
	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, Map<String, String>> actions;

	@Schema
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonIgnore
	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean active;

	@Schema
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	@Schema
	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateModified;

	@Schema
	public String getDefaultLanguageId() {
		return defaultLanguageId;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		this.defaultLanguageId = defaultLanguageId;
	}

	@JsonIgnore
	public void setDefaultLanguageId(
		UnsafeSupplier<String, Exception> defaultLanguageIdUnsafeSupplier) {

		try {
			defaultLanguageId = defaultLanguageIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String defaultLanguageId;

	@Schema
	public Boolean getEnableCategorization() {
		return enableCategorization;
	}

	public void setEnableCategorization(Boolean enableCategorization) {
		this.enableCategorization = enableCategorization;
	}

	@JsonIgnore
	public void setEnableCategorization(
		UnsafeSupplier<Boolean, Exception> enableCategorizationUnsafeSupplier) {

		try {
			enableCategorization = enableCategorizationUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean enableCategorization;

	@Schema
	public Boolean getEnableComments() {
		return enableComments;
	}

	public void setEnableComments(Boolean enableComments) {
		this.enableComments = enableComments;
	}

	@JsonIgnore
	public void setEnableComments(
		UnsafeSupplier<Boolean, Exception> enableCommentsUnsafeSupplier) {

		try {
			enableComments = enableCommentsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean enableComments;

	@Schema
	public Boolean getEnableLocalization() {
		return enableLocalization;
	}

	public void setEnableLocalization(Boolean enableLocalization) {
		this.enableLocalization = enableLocalization;
	}

	@JsonIgnore
	public void setEnableLocalization(
		UnsafeSupplier<Boolean, Exception> enableLocalizationUnsafeSupplier) {

		try {
			enableLocalization = enableLocalizationUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean enableLocalization;

	@Schema
	public Boolean getEnableObjectEntryDraft() {
		return enableObjectEntryDraft;
	}

	public void setEnableObjectEntryDraft(Boolean enableObjectEntryDraft) {
		this.enableObjectEntryDraft = enableObjectEntryDraft;
	}

	@JsonIgnore
	public void setEnableObjectEntryDraft(
		UnsafeSupplier<Boolean, Exception>
			enableObjectEntryDraftUnsafeSupplier) {

		try {
			enableObjectEntryDraft = enableObjectEntryDraftUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean enableObjectEntryDraft;

	@Schema
	public Boolean getEnableObjectEntryHistory() {
		return enableObjectEntryHistory;
	}

	public void setEnableObjectEntryHistory(Boolean enableObjectEntryHistory) {
		this.enableObjectEntryHistory = enableObjectEntryHistory;
	}

	@JsonIgnore
	public void setEnableObjectEntryHistory(
		UnsafeSupplier<Boolean, Exception>
			enableObjectEntryHistoryUnsafeSupplier) {

		try {
			enableObjectEntryHistory =
				enableObjectEntryHistoryUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean enableObjectEntryHistory;

	@Schema
	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	@JsonIgnore
	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String externalReferenceCode;

	@Schema
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@Schema
	@Valid
	public Map<String, String> getLabel() {
		return label;
	}

	public void setLabel(Map<String, String> label) {
		this.label = label;
	}

	@JsonIgnore
	public void setLabel(
		UnsafeSupplier<Map<String, String>, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> label;

	@Schema
	public Boolean getModifiable() {
		return modifiable;
	}

	public void setModifiable(Boolean modifiable) {
		this.modifiable = modifiable;
	}

	@JsonIgnore
	public void setModifiable(
		UnsafeSupplier<Boolean, Exception> modifiableUnsafeSupplier) {

		try {
			modifiable = modifiableUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean modifiable;

	@Schema
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	@Schema
	@Valid
	public ObjectAction[] getObjectActions() {
		return objectActions;
	}

	public void setObjectActions(ObjectAction[] objectActions) {
		this.objectActions = objectActions;
	}

	@JsonIgnore
	public void setObjectActions(
		UnsafeSupplier<ObjectAction[], Exception> objectActionsUnsafeSupplier) {

		try {
			objectActions = objectActionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ObjectAction[] objectActions;

	@Schema
	@Valid
	public ObjectField[] getObjectFields() {
		return objectFields;
	}

	public void setObjectFields(ObjectField[] objectFields) {
		this.objectFields = objectFields;
	}

	@JsonIgnore
	public void setObjectFields(
		UnsafeSupplier<ObjectField[], Exception> objectFieldsUnsafeSupplier) {

		try {
			objectFields = objectFieldsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ObjectField[] objectFields;

	@Schema
	public String getObjectFolderExternalReferenceCode() {
		return objectFolderExternalReferenceCode;
	}

	public void setObjectFolderExternalReferenceCode(
		String objectFolderExternalReferenceCode) {

		this.objectFolderExternalReferenceCode =
			objectFolderExternalReferenceCode;
	}

	@JsonIgnore
	public void setObjectFolderExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			objectFolderExternalReferenceCodeUnsafeSupplier) {

		try {
			objectFolderExternalReferenceCode =
				objectFolderExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String objectFolderExternalReferenceCode;

	@Schema
	@Valid
	public ObjectLayout[] getObjectLayouts() {
		return objectLayouts;
	}

	public void setObjectLayouts(ObjectLayout[] objectLayouts) {
		this.objectLayouts = objectLayouts;
	}

	@JsonIgnore
	public void setObjectLayouts(
		UnsafeSupplier<ObjectLayout[], Exception> objectLayoutsUnsafeSupplier) {

		try {
			objectLayouts = objectLayoutsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ObjectLayout[] objectLayouts;

	@Schema
	@Valid
	public ObjectRelationship[] getObjectRelationships() {
		return objectRelationships;
	}

	public void setObjectRelationships(
		ObjectRelationship[] objectRelationships) {

		this.objectRelationships = objectRelationships;
	}

	@JsonIgnore
	public void setObjectRelationships(
		UnsafeSupplier<ObjectRelationship[], Exception>
			objectRelationshipsUnsafeSupplier) {

		try {
			objectRelationships = objectRelationshipsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ObjectRelationship[] objectRelationships;

	@Schema
	@Valid
	public ObjectValidationRule[] getObjectValidationRules() {
		return objectValidationRules;
	}

	public void setObjectValidationRules(
		ObjectValidationRule[] objectValidationRules) {

		this.objectValidationRules = objectValidationRules;
	}

	@JsonIgnore
	public void setObjectValidationRules(
		UnsafeSupplier<ObjectValidationRule[], Exception>
			objectValidationRulesUnsafeSupplier) {

		try {
			objectValidationRules = objectValidationRulesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ObjectValidationRule[] objectValidationRules;

	@Schema
	@Valid
	public ObjectView[] getObjectViews() {
		return objectViews;
	}

	public void setObjectViews(ObjectView[] objectViews) {
		this.objectViews = objectViews;
	}

	@JsonIgnore
	public void setObjectViews(
		UnsafeSupplier<ObjectView[], Exception> objectViewsUnsafeSupplier) {

		try {
			objectViews = objectViewsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ObjectView[] objectViews;

	@Schema
	public String getPanelAppOrder() {
		return panelAppOrder;
	}

	public void setPanelAppOrder(String panelAppOrder) {
		this.panelAppOrder = panelAppOrder;
	}

	@JsonIgnore
	public void setPanelAppOrder(
		UnsafeSupplier<String, Exception> panelAppOrderUnsafeSupplier) {

		try {
			panelAppOrder = panelAppOrderUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String panelAppOrder;

	@Schema
	public String getPanelCategoryKey() {
		return panelCategoryKey;
	}

	public void setPanelCategoryKey(String panelCategoryKey) {
		this.panelCategoryKey = panelCategoryKey;
	}

	@JsonIgnore
	public void setPanelCategoryKey(
		UnsafeSupplier<String, Exception> panelCategoryKeyUnsafeSupplier) {

		try {
			panelCategoryKey = panelCategoryKeyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String panelCategoryKey;

	@Schema
	public Boolean getParameterRequired() {
		return parameterRequired;
	}

	public void setParameterRequired(Boolean parameterRequired) {
		this.parameterRequired = parameterRequired;
	}

	@JsonIgnore
	public void setParameterRequired(
		UnsafeSupplier<Boolean, Exception> parameterRequiredUnsafeSupplier) {

		try {
			parameterRequired = parameterRequiredUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Boolean parameterRequired;

	@Schema
	@Valid
	public Map<String, String> getPluralLabel() {
		return pluralLabel;
	}

	public void setPluralLabel(Map<String, String> pluralLabel) {
		this.pluralLabel = pluralLabel;
	}

	@JsonIgnore
	public void setPluralLabel(
		UnsafeSupplier<Map<String, String>, Exception>
			pluralLabelUnsafeSupplier) {

		try {
			pluralLabel = pluralLabelUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> pluralLabel;

	@Schema
	public Boolean getPortlet() {
		return portlet;
	}

	public void setPortlet(Boolean portlet) {
		this.portlet = portlet;
	}

	@JsonIgnore
	public void setPortlet(
		UnsafeSupplier<Boolean, Exception> portletUnsafeSupplier) {

		try {
			portlet = portletUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean portlet;

	@Schema
	public String getRestContextPath() {
		return restContextPath;
	}

	public void setRestContextPath(String restContextPath) {
		this.restContextPath = restContextPath;
	}

	@JsonIgnore
	public void setRestContextPath(
		UnsafeSupplier<String, Exception> restContextPathUnsafeSupplier) {

		try {
			restContextPath = restContextPathUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String restContextPath;

	@Schema
	public String getRootObjectDefinitionExternalReferenceCode() {
		return rootObjectDefinitionExternalReferenceCode;
	}

	public void setRootObjectDefinitionExternalReferenceCode(
		String rootObjectDefinitionExternalReferenceCode) {

		this.rootObjectDefinitionExternalReferenceCode =
			rootObjectDefinitionExternalReferenceCode;
	}

	@JsonIgnore
	public void setRootObjectDefinitionExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			rootObjectDefinitionExternalReferenceCodeUnsafeSupplier) {

		try {
			rootObjectDefinitionExternalReferenceCode =
				rootObjectDefinitionExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String rootObjectDefinitionExternalReferenceCode;

	@Schema
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@JsonIgnore
	public void setScope(
		UnsafeSupplier<String, Exception> scopeUnsafeSupplier) {

		try {
			scope = scopeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String scope;

	@Schema
	@Valid
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<Status, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Status status;

	@Schema
	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	@JsonIgnore
	public void setStorageType(
		UnsafeSupplier<String, Exception> storageTypeUnsafeSupplier) {

		try {
			storageType = storageTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String storageType;

	@Schema
	public Boolean getSystem() {
		return system;
	}

	public void setSystem(Boolean system) {
		this.system = system;
	}

	@JsonIgnore
	public void setSystem(
		UnsafeSupplier<Boolean, Exception> systemUnsafeSupplier) {

		try {
			system = systemUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean system;

	@Schema
	public String getTitleObjectFieldName() {
		return titleObjectFieldName;
	}

	public void setTitleObjectFieldName(String titleObjectFieldName) {
		this.titleObjectFieldName = titleObjectFieldName;
	}

	@JsonIgnore
	public void setTitleObjectFieldName(
		UnsafeSupplier<String, Exception> titleObjectFieldNameUnsafeSupplier) {

		try {
			titleObjectFieldName = titleObjectFieldNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String titleObjectFieldName;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectDefinition)) {
			return false;
		}

		ObjectDefinition objectDefinition = (ObjectDefinition)object;

		return Objects.equals(toString(), objectDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (accountEntryRestricted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryRestricted\": ");

			sb.append(accountEntryRestricted);
		}

		if (accountEntryRestrictedObjectFieldName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryRestrictedObjectFieldName\": ");

			sb.append("\"");

			sb.append(_escape(accountEntryRestrictedObjectFieldName));

			sb.append("\"");
		}

		if (actions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(actions));
		}

		if (active != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(active);
		}

		if (dateCreated != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateCreated));

			sb.append("\"");
		}

		if (dateModified != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateModified));

			sb.append("\"");
		}

		if (defaultLanguageId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultLanguageId\": ");

			sb.append("\"");

			sb.append(_escape(defaultLanguageId));

			sb.append("\"");
		}

		if (enableCategorization != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableCategorization\": ");

			sb.append(enableCategorization);
		}

		if (enableComments != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableComments\": ");

			sb.append(enableComments);
		}

		if (enableLocalization != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableLocalization\": ");

			sb.append(enableLocalization);
		}

		if (enableObjectEntryDraft != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableObjectEntryDraft\": ");

			sb.append(enableObjectEntryDraft);
		}

		if (enableObjectEntryHistory != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enableObjectEntryHistory\": ");

			sb.append(enableObjectEntryHistory);
		}

		if (externalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceCode));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (label != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append(_toJSON(label));
		}

		if (modifiable != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiable\": ");

			sb.append(modifiable);
		}

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		if (objectActions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectActions\": ");

			sb.append("[");

			for (int i = 0; i < objectActions.length; i++) {
				sb.append(String.valueOf(objectActions[i]));

				if ((i + 1) < objectActions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectFields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectFields\": ");

			sb.append("[");

			for (int i = 0; i < objectFields.length; i++) {
				sb.append(String.valueOf(objectFields[i]));

				if ((i + 1) < objectFields.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectFolderExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectFolderExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(objectFolderExternalReferenceCode));

			sb.append("\"");
		}

		if (objectLayouts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectLayouts\": ");

			sb.append("[");

			for (int i = 0; i < objectLayouts.length; i++) {
				sb.append(String.valueOf(objectLayouts[i]));

				if ((i + 1) < objectLayouts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectRelationships != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectRelationships\": ");

			sb.append("[");

			for (int i = 0; i < objectRelationships.length; i++) {
				sb.append(String.valueOf(objectRelationships[i]));

				if ((i + 1) < objectRelationships.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectValidationRules != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectValidationRules\": ");

			sb.append("[");

			for (int i = 0; i < objectValidationRules.length; i++) {
				sb.append(String.valueOf(objectValidationRules[i]));

				if ((i + 1) < objectValidationRules.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectViews != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectViews\": ");

			sb.append("[");

			for (int i = 0; i < objectViews.length; i++) {
				sb.append(String.valueOf(objectViews[i]));

				if ((i + 1) < objectViews.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (panelAppOrder != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"panelAppOrder\": ");

			sb.append("\"");

			sb.append(_escape(panelAppOrder));

			sb.append("\"");
		}

		if (panelCategoryKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"panelCategoryKey\": ");

			sb.append("\"");

			sb.append(_escape(panelCategoryKey));

			sb.append("\"");
		}

		if (parameterRequired != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameterRequired\": ");

			sb.append(parameterRequired);
		}

		if (pluralLabel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pluralLabel\": ");

			sb.append(_toJSON(pluralLabel));
		}

		if (portlet != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portlet\": ");

			sb.append(portlet);
		}

		if (restContextPath != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"restContextPath\": ");

			sb.append("\"");

			sb.append(_escape(restContextPath));

			sb.append("\"");
		}

		if (rootObjectDefinitionExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rootObjectDefinitionExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(rootObjectDefinitionExternalReferenceCode));

			sb.append("\"");
		}

		if (scope != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"scope\": ");

			sb.append("\"");

			sb.append(_escape(scope));

			sb.append("\"");
		}

		if (status != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(String.valueOf(status));
		}

		if (storageType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"storageType\": ");

			sb.append("\"");

			sb.append(_escape(storageType));

			sb.append("\"");
		}

		if (system != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"system\": ");

			sb.append(system);
		}

		if (titleObjectFieldName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"titleObjectFieldName\": ");

			sb.append("\"");

			sb.append(_escape(titleObjectFieldName));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}