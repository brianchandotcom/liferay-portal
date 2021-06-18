<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
List<AssetRendererFactory<?>> classTypesAssetRendererFactories = new ArrayList<>();
List<Map<String, Object>> classTypesList = new ArrayList<>();
%>

<liferay-frontend:fieldset-group>
	<liferay-frontend:fieldset
		cssClass="source-container"
		disabled="<%= editAssetListDisplayContext.isLiveGroup() %>"
	>

		<%

		// Left list

		List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

		long[] classNameIds = editAssetListDisplayContext.getClassNameIds();

		for (long classNameId : classNameIds) {
			typesLeftList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, PortalUtil.getClassName(classNameId))));
		}

		// Right list

		List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

		Arrays.sort(classNameIds);
		%>

		<aui:select label="item-type" name="TypeSettingsProperties--anyAssetType--" title="item-type">
			<aui:option label='<%= StringPool.DASH + LanguageUtil.get(request, "not-selected") + StringPool.DASH %>' selected="<%= editAssetListDisplayContext.isNoAssetTypeSelected() %>" value="" />

			<optgroup label="<liferay-ui:message key="single-item-type" />">

				<%
				for (long classNameId : editAssetListDisplayContext.getAvailableClassNameIds()) {
					ClassName className = ClassNameLocalServiceUtil.getClassName(classNameId);

					if (Arrays.binarySearch(classNameIds, classNameId) < 0) {
						typesRightList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className.getValue())));
					}
				%>

					<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, className.getValue()) %>" selected="<%= (classNameIds.length == 1) && (classNameId == classNameIds[0]) %>" value="<%= classNameId %>" />

				<%
				}
				%>

			</optgroup>

			<optgroup label="<liferay-ui:message key="multiple-item-types" />">
				<aui:option label='<%= LanguageUtil.get(request, "select-types") + StringPool.TRIPLE_PERIOD %>' selected="<%= !editAssetListDisplayContext.isAnyAssetType() && !editAssetListDisplayContext.isNoAssetTypeSelected() && (classNameIds.length > 1) %>" value="<%= false %>" />
				<aui:option label="all-types" selected="<%= editAssetListDisplayContext.isAnyAssetType() %>" value="<%= true %>" />
			</optgroup>
		</aui:select>

		<aui:input name="TypeSettingsProperties--classNameIds--" type="hidden" />

		<%
		typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
		%>

		<div class="<%= editAssetListDisplayContext.isAnyAssetType() ? "hide" : "" %>" id="<portlet:namespace />classNamesBoxes">
			<liferay-ui:input-move-boxes
				leftBoxName="currentClassNameIds"
				leftList="<%= typesLeftList %>"
				leftReorder="<%= Boolean.TRUE.toString() %>"
				leftTitle="selected"
				rightBoxName="availableClassNameIds"
				rightList="<%= typesRightList %>"
				rightTitle="available"
			/>
		</div>

		<%
		UnicodeProperties properties = editAssetListDisplayContext.getUnicodeProperties();

		List<AssetRendererFactory<?>> assetRendererFactories = ListUtil.sort(AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId()), new AssetRendererFactoryTypeNameComparator(locale));

		for (AssetRendererFactory<?> assetRendererFactory : assetRendererFactories) {
			ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();

			List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(editAssetListDisplayContext.getReferencedModelsGroupIds(), locale);

			if (classTypes.isEmpty()) {
				continue;
			}

			classTypes.sort(new ClassTypeNameComparator(true));

			classTypesAssetRendererFactories.add(assetRendererFactory);

			String className = editAssetListDisplayContext.getClassName(assetRendererFactory);

			Long[] assetSelectedClassTypeIds = editAssetListDisplayContext.getClassTypeIds(properties, className, classTypes);

			// Left list

			List<KeyValuePair> subtypesLeftList = new ArrayList<KeyValuePair>();

			for (long subtypeId : assetSelectedClassTypeIds) {
				try {
					ClassType classType = classTypeReader.getClassType(subtypeId, locale);

					subtypesLeftList.add(new KeyValuePair(String.valueOf(subtypeId), HtmlUtil.escape(classType.getName())));
				}
				catch (NoSuchModelException nsme) {
				}
			}

			Arrays.sort(assetSelectedClassTypeIds);

			// Right list

			List<KeyValuePair> subtypesRightList = new ArrayList<KeyValuePair>();

			boolean noAssetSubtypeSelected = false;

			if (Validator.isNull(properties.getProperty("anyClassType" + className))) {
				noAssetSubtypeSelected = true;
			}

			boolean anyAssetSubtype = GetterUtil.getBoolean(properties.getProperty("anyClassType" + className, Boolean.TRUE.toString()));

			if (noAssetSubtypeSelected) {
				anyAssetSubtype = false;
			}
		%>

			<div class='asset-subtype <%= (assetSelectedClassTypeIds.length < 1) ? StringPool.BLANK : "hide" %>' id="<portlet:namespace /><%= className %>Options">
				<aui:select label='<%= LanguageUtil.get(request, "item-subtype") %>' name='<%= "TypeSettingsProperties--anyClassType" + className + "--" %>'>
					<aui:option label='<%= StringPool.DASH + LanguageUtil.get(request, "not-selected") + StringPool.DASH %>' selected="<%= editAssetListDisplayContext.isNoAssetTypeSelected() %>" value="" />

					<optgroup label="<%= LanguageUtil.get(request, "single-item-subtype") %>">

						<%
						for (ClassType classType : classTypes) {
							if (Arrays.binarySearch(assetSelectedClassTypeIds, classType.getClassTypeId()) < 0) {
								subtypesRightList.add(new KeyValuePair(String.valueOf(classType.getClassTypeId()), HtmlUtil.escape(classType.getName())));
							}
						%>

							<aui:option label="<%= HtmlUtil.escapeAttribute(classType.getName()) %>" selected="<%= !anyAssetSubtype && (assetSelectedClassTypeIds.length == 1) && (assetSelectedClassTypeIds[0]).equals(classType.getClassTypeId()) && !noAssetSubtypeSelected %>" value="<%= classType.getClassTypeId() %>" />

						<%
						}
						%>

					</optgroup>

					<optgroup label="<%= LanguageUtil.get(request, "multiple-item-subtypes") %>">
						<aui:option label='<%= LanguageUtil.get(request, "select-more-than-one") + StringPool.TRIPLE_PERIOD %>' selected="<%= !anyAssetSubtype && (assetSelectedClassTypeIds.length > 1) && !noAssetSubtypeSelected %>" value="<%= false %>" />
						<aui:option label="all-subtypes" selected="<%= anyAssetSubtype %>" value="<%= true %>" />
					</optgroup>
				</aui:select>

				<aui:input name='<%= "TypeSettingsProperties--classTypeIds" + className + "--" %>' type="hidden" />

				<c:if test="<%= assetListDisplayContext.getAssetListEntryType() == AssetListEntryTypeConstants.TYPE_DYNAMIC %>">
					<div class="asset-subtypefields-wrapper-enable hide" id="<portlet:namespace /><%= className %>subtypeFieldsFilterEnableWrapper">
						<aui:input inlineLabel="right" label="filter-by-field" labelCssClass="simple-toggle-switch" name='<%= "TypeSettingsProperties--subtypeFieldsFilterEnabled" + className + "--" %>' type="toggle-switch" value="<%= editAssetListDisplayContext.isSubtypeFieldsFilterEnabled() %>" />
					</div>

					<span class="asset-subtypefields-message" id="<portlet:namespace /><%= className %>ddmStructureFieldMessage">
						<c:if test="<%= Validator.isNotNull(editAssetListDisplayContext.getDDMStructureFieldLabel()) && (classNameIds[0] == PortalUtil.getClassNameId(assetRendererFactory.getClassName())) %>">
							<%= HtmlUtil.escape(editAssetListDisplayContext.getDDMStructureFieldLabel()) + ": " + HtmlUtil.escape(editAssetListDisplayContext.getDDMStructureDisplayFieldValue()) %>
						</c:if>
					</span>

					<div class="asset-subtypefields-wrapper hide" id="<portlet:namespace /><%= className %>subtypeFieldsWrapper">

						<%
						for (ClassType classType : classTypes) {
							if (classType.getClassTypeFieldsCount() == 0) {
								continue;
							}
						%>

							<span class="asset-subtypefields hide" id="<portlet:namespace /><%= classType.getClassTypeId() %>_<%= className %>Options">
								<portlet:renderURL var="selectStructureFieldURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
									<portlet:param name="mvcPath" value="/asset_list/select_structure_field.jsp" />
									<portlet:param name="className" value="<%= assetRendererFactory.getClassName() %>" />
									<portlet:param name="classTypeId" value="<%= String.valueOf(classType.getClassTypeId()) %>" />
									<portlet:param name="eventName" value='<%= liferayPortletResponse.getNamespace() + "selectDDMStructureField" %>' />
								</portlet:renderURL>

								<span class="asset-subtypefields-popup" id="<portlet:namespace /><%= classType.getClassTypeId() %>_<%= className %>PopUpButton">
									<aui:button data-href="<%= selectStructureFieldURL.toString() %>" disabled="<%= !editAssetListDisplayContext.isSubtypeFieldsFilterEnabled() %>" value="select" />
								</span>
							</span>

						<%
						}

						typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
						%>

					</div>
				</c:if>

				<div class="<%= (assetSelectedClassTypeIds.length > 1) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace /><%= className %>Boxes">
					<liferay-ui:input-move-boxes
						leftBoxName='<%= className + "currentClassTypeIds" %>'
						leftList="<%= subtypesLeftList %>"
						leftReorder="<%= Boolean.TRUE.toString() %>"
						leftTitle="selected"
						rightBoxName='<%= className + "availableClassTypeIds" %>'
						rightList="<%= subtypesRightList %>"
						rightTitle="available"
					/>
				</div>
			</div>

		<%
		}

		for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
			ClassTypeReader classTypeReader = curRendererFactory.getClassTypeReader();

			List<Map<String, Object>> classSubtypes = new ArrayList<>();

			List<ClassType> assetAvailableClassTypes = classTypeReader.getAvailableClassTypes(editAssetListDisplayContext.getReferencedModelsGroupIds(), locale);

			if (assetAvailableClassTypes.isEmpty()) {
				continue;
			}

			for (ClassType classType : assetAvailableClassTypes) {
				List<ClassTypeField> classTypeFields = classType.getClassTypeFields();

				List<Map<String, Object>> classTypeFieldsList = new ArrayList<>();

				if (classTypeFields.isEmpty()) {
					continue;
				}

				String orderByColumn1 = editAssetListDisplayContext.getOrderByColumn1();
				String orderByColumn2 = editAssetListDisplayContext.getOrderByColumn2();

				for (ClassTypeField classTypeField : classTypeFields) {
					String value = editAssetListDisplayContext.encodeName(classTypeField.getClassTypeId(), classTypeField.getFieldReference(), null);
					String selectedOrderByColumn1 = StringPool.BLANK;
					String selectedOrderByColumn2 = StringPool.BLANK;

					if (orderByColumn1.equals(value)) {
						selectedOrderByColumn1 = "selected";
					}

					if (orderByColumn2.equals(value)) {
						selectedOrderByColumn2 = "selected";
					}

					classTypeFieldsList.add(
						HashMapBuilder.<String, Object>put(
							"label", HtmlUtil.escape(classTypeField.getLabel())
						).put(
							"selectedOrderByColumn1", selectedOrderByColumn1
						).put(
							"selectedOrderByColumn2", selectedOrderByColumn2
						).put(
							"value", value
						).build());
				}

				classSubtypes.add(
					HashMapBuilder.<String, Object>put(
						"classTypeFields", classTypeFieldsList
					).put(
						"classTypeId", classType.getClassTypeId()
					).put(
						"name", HtmlUtil.escape(classType.getName())
					).build());
			}

			classTypesList.add(
				HashMapBuilder.<String, Object>put(
					"className", editAssetListDisplayContext.getClassName(curRendererFactory)
				).put(
					"classNameId", curRendererFactory.getClassNameId()
				).put(
					"classSubtypes", classSubtypes
				).build());
		}
		%>

		<div class="asset-subtypefield-selected <%= Validator.isNull(editAssetListDisplayContext.getDDMStructureFieldName()) ? "hide" : StringPool.BLANK %>">
			<aui:input name="TypeSettingsProperties--ddmStructureFieldName--" type="hidden" value="<%= editAssetListDisplayContext.getDDMStructureFieldName() %>" />

			<aui:input name="TypeSettingsProperties--ddmStructureFieldValue--" type="hidden" value="<%= editAssetListDisplayContext.getDDMStructureFieldValue() %>" />

			<aui:input name="TypeSettingsProperties--ddmStructureDisplayFieldValue--" type="hidden" value="<%= editAssetListDisplayContext.getDDMStructureDisplayFieldValue() %>" />
		</div>
	</liferay-frontend:fieldset>
</liferay-frontend:fieldset-group>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "selectDDMStructureField" %>'
	context='<%=
		HashMapBuilder.<String, Object>put(
			"classTypes", classTypesList
		).build()
	%>'
	module="js/Source"
/>