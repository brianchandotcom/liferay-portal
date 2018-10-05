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
List<AssetRendererFactory<?>> classTypesAssetRendererFactories = new ArrayList();
%>

<portlet:actionURL name="/asset_list/edit_asset_list_entry_settings" var="editAssetListEntrySettingsURL" />

<liferay-frontend:edit-form
	action="<%= editAssetListEntrySettingsURL %>"
	method="post"
	name="fm"
	onSubmit="event.preventDefault();"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />

	<liferay-frontend:edit-form-body>
		<h1 class="sheet-title">
			<liferay-ui:message key="source" />
		</h1>

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				cssClass="source-container"
			>

				<%
				Set<Long> availableClassNameIdsSet = SetUtil.fromArray(editAssetListDisplayContext.getAvailableClassNameIds());

				// Left list

				List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

				long[] classNameIds = editAssetListDisplayContext.getClassNameIds();

				for (long classNameId : classNameIds) {
					String className = PortalUtil.getClassName(classNameId);

					typesLeftList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className)));
				}

				// Right list

				List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

				Arrays.sort(classNameIds);
				%>

				<aui:select label="asset-entry-type" name="TypeSettingsProperties--anyAssetType--" title="asset-type">
					<aui:option label="any" selected="<%= editAssetListDisplayContext.isAnyAssetType() %>" value="<%= true %>" />
					<aui:option label='<%= LanguageUtil.get(request, "select-more-than-one") + StringPool.TRIPLE_PERIOD %>' selected="<%= !editAssetListDisplayContext.isAnyAssetType() && (classNameIds.length > 1) %>" value="<%= false %>" />

					<optgroup label="<liferay-ui:message key="asset-type" />">

						<%
						for (long classNameId : availableClassNameIdsSet) {
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
				List<AssetRendererFactory<?>> assetRendererFactories = ListUtil.sort(AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId()), new AssetRendererFactoryTypeNameComparator(locale));

				for (AssetRendererFactory<?> assetRendererFactory : assetRendererFactories) {
					ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();

					List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(editAssetListDisplayContext.getReferencedModelsGroupIds(), locale);

					if (classTypes.isEmpty()) {
						continue;
					}

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

					boolean anyAssetSubtype = GetterUtil.getBoolean(properties.getProperty("anyClassType" + className, Boolean.TRUE.toString()));
				%>

					<div class='asset-subtype <%= (assetSelectedClassTypeIds.length < 1) ? StringPool.BLANK : "hide" %>' id="<portlet:namespace /><%= className %>Options">

						<%
						String label = ResourceActionsUtil.getModelResource(locale, assetRendererFactory.getClassName()) + StringPool.SPACE + assetRendererFactory.getSubtypeTitle(themeDisplay.getLocale());
						%>

						<aui:select label="<%= label %>" name='<%= "TypeSettingsProperties--anyClassType" + className + "--" %>'>
							<aui:option label="any" selected="<%= anyAssetSubtype %>" value="<%= true %>" />
							<aui:option label='<%= LanguageUtil.get(request, "select-more-than-one") + StringPool.TRIPLE_PERIOD %>' selected="<%= !anyAssetSubtype && (assetSelectedClassTypeIds.length > 1) %>" value="<%= false %>" />

							<optgroup label="<%= assetRendererFactory.getSubtypeTitle(themeDisplay.getLocale()) %>">

								<%
								for (ClassType classType : classTypes) {
									if (Arrays.binarySearch(assetSelectedClassTypeIds, classType.getClassTypeId()) < 0) {
										subtypesRightList.add(new KeyValuePair(String.valueOf(classType.getClassTypeId()), HtmlUtil.escape(classType.getName())));
									}
								%>

									<aui:option label="<%= HtmlUtil.escapeAttribute(classType.getName()) %>" selected="<%= !anyAssetSubtype && (assetSelectedClassTypeIds.length == 1) && (assetSelectedClassTypeIds[0]).equals(classType.getClassTypeId()) %>" value="<%= classType.getClassTypeId() %>" />

								<%
								}
								%>

							</optgroup>
						</aui:select>

						<aui:input name='<%= "TypeSettingsProperties--classTypeIds" + className + "--" %>' type="hidden" />

						<c:if test="<%= editAssetListDisplayContext.isShowSubtypeFieldsFilter() %>">
							<div class="asset-subtypefields-wrapper-enable hide" id="<portlet:namespace /><%= className %>subtypeFieldsFilterEnableWrapper">
								<aui:input label="filter-by-field" name='<%= "TypeSettingsProperties--subtypeFieldsFilterEnabled" + className + "--" %>' type="toggle-switch" value="<%= editAssetListDisplayContext.isSubtypeFieldsFilterEnabled() %>" />
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
											<portlet:param name="eventName" value='<%= renderResponse.getNamespace() + "selectDDMStructureField" %>' />
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
				%>

				<c:if test="<%= editAssetListDisplayContext.isShowSubtypeFieldsFilter() %>">
					<div class="asset-subtypefield-selected <%= Validator.isNull(editAssetListDisplayContext.getDDMStructureFieldName()) ? "hide" : StringPool.BLANK %>">
						<aui:input name="TypeSettingsProperties--ddmStructureFieldName--" type="hidden" value="<%= editAssetListDisplayContext.getDDMStructureFieldName() %>" />

						<aui:input name="TypeSettingsProperties--ddmStructureFieldValue--" type="hidden" value="<%= editAssetListDisplayContext.getDDMStructureFieldValue() %>" />

						<aui:input name="TypeSettingsProperties--ddmStructureDisplayFieldValue--" type="hidden" value="<%= editAssetListDisplayContext.getDDMStructureDisplayFieldValue() %>" />
					</div>
				</c:if>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />

		<aui:button href="<%= editAssetListDisplayContext.getRedirectURL() %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	var Util = Liferay.Util;

	var assetMultipleSelector = $('#<portlet:namespace />currentClassNameIds');
	var assetSelector = $('#<portlet:namespace />anyAssetType');
	var ddmStructureFieldName = $('#<portlet:namespace />ddmStructureFieldName');
	var sourcePanel = $('.source-container');

	<%
	for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
		String className = editAssetListDisplayContext.getClassName(curRendererFactory);
	%>

		Util.toggleSelectBox('<portlet:namespace />anyClassType<%= className %>', 'false', '<portlet:namespace /><%= className %>Boxes');

		var <%= className %>Options = $('#<portlet:namespace /><%= className %>Options');

		function <portlet:namespace />toggle<%= className %>() {
			var assetOptions = assetMultipleSelector.find('option');

			var showOptions = ((assetSelector.val() == '<%= curRendererFactory.getClassNameId() %>') ||
				((assetSelector.val() == 'false') && (assetOptions.length == 1) && (assetOptions.eq(0).val() == '<%= curRendererFactory.getClassNameId() %>')));

			<%= className %>Options.toggleClass('hide', !showOptions);

			<c:if test="<%= editAssetListDisplayContext.isShowSubtypeFieldsFilter() %>">
				<%= className %>toggleSubclassesFields(true);
			</c:if>
		}

		<%
		ClassTypeReader classTypeReader = curRendererFactory.getClassTypeReader();

		List<ClassType> assetAvailableClassTypes = classTypeReader.getAvailableClassTypes(editAssetListDisplayContext.getReferencedModelsGroupIds(), locale);

		if (assetAvailableClassTypes.isEmpty() || !editAssetListDisplayContext.isShowSubtypeFieldsFilter()) {
			continue;
		}
		%>

		var <%= className %>SubtypeSelector = $('#<portlet:namespace />anyClassType<%= className %>');

		function <%= className %>toggleSubclassesFields(hideSubtypeFilterEnableWrapper) {
			var subtypeFieldsWrapper = $('#<portlet:namespace /><%= className %>subtypeFieldsWrapper, #<portlet:namespace /><%= className %>subtypeFieldsFilterEnableWrapper');

			var selectedSubtype = <%= className %>SubtypeSelector.val();

			var structureOptions = $('#<portlet:namespace />' + selectedSubtype + '_<%= className %>Options');

			structureOptions.removeClass('hide');

			if ((selectedSubtype != 'false') && (selectedSubtype != 'true')) {
				if (structureOptions.length) {
					subtypeFieldsWrapper.removeClass('hide');
				}
				else if (hideSubtypeFilterEnableWrapper) {
					subtypeFieldsWrapper.addClass('hide');
				}
			}
			else if (hideSubtypeFilterEnableWrapper) {
				subtypeFieldsWrapper.addClass('hide');
			}
		}

		<%= className %>toggleSubclassesFields(false);

		<%= className %>SubtypeSelector.on(
			'change',
			function(event) {
				setDDMFields('<%= className %>', '', '', '', '');

				var subtypeFieldsFilterEnabled = $('#<portlet:namespace />subtypeFieldsFilterEnabled<%= className %>');

				subtypeFieldsFilterEnabled.prop('checked', false);

				sourcePanel.find('.asset-subtypefields').addClass('hide');

				<%= className %>toggleSubclassesFields(true);
			}
		);

	<%
	}
	%>

	function <portlet:namespace />toggleSubclasses() {

		<%
		for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
			String className = editAssetListDisplayContext.getClassName(curRendererFactory);
		%>

			<portlet:namespace />toggle<%= className %>();

		<%
		}
		%>

	}

	<portlet:namespace />toggleSubclasses();

	assetSelector.on(
		'change',
		function(event) {
			ddmStructureFieldName.val('');

			$('#<portlet:namespace />ddmStructureFieldValue').val('');

			<portlet:namespace />toggleSubclasses();
		}
	);

	sourcePanel.on(
		'click',
		'.asset-subtypefields-wrapper-enable label',
		function(event) {
			var assetSubtypeFieldsPopupNodes = $('.asset-subtypefields-popup .btn');

			Util.toggleDisabled(assetSubtypeFieldsPopupNodes, !$(event.target).prop('checked'));
		}
	);

	Liferay.after(
		'inputmoveboxes:moveItem',
		function(event) {
			if ((event.fromBox.attr('id') == '<portlet:namespace />currentClassNameIds') || (event.toBox.attr('id') == '<portlet:namespace />currentClassNameIds')) {
				<portlet:namespace />toggleSubclasses();
			}
		}
	);

	sourcePanel.on(
		'click',
		'.asset-subtypefields-popup',
		function(event) {
			var currentTarget = $(event.currentTarget);

			var btn = $('.btn', currentTarget);

			var uri = btn.data('href');

			uri = Util.addParams('<portlet:namespace />ddmStructureDisplayFieldValue=' + encodeURIComponent($('#<portlet:namespace />ddmStructureDisplayFieldValue').val()), uri);
			uri = Util.addParams('<portlet:namespace />ddmStructureFieldName=' + encodeURIComponent($('#<portlet:namespace />ddmStructureFieldName').val()), uri);
			uri = Util.addParams('<portlet:namespace />ddmStructureFieldValue=' + encodeURIComponent($('#<portlet:namespace />ddmStructureFieldValue').val()), uri);

			Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					eventName: '<portlet:namespace />selectDDMStructureField',
					id: '<portlet:namespace />selectDDMStructure' + currentTarget.attr('id'),
					title: '<liferay-ui:message arguments="structure-field" key="select-x" />',
					uri: uri
				},
				function(event) {
					setDDMFields(event.className, event.name, event.value, event.displayValue, event.label + ': ' + event.displayValue);
				}
			);
		}
	);

	function setDDMFields(className, name, value, displayValue, message) {
		$('#<portlet:namespace />ddmStructureFieldName').val(name);
		$('#<portlet:namespace />ddmStructureFieldValue').val(value);
		$('#<portlet:namespace />ddmStructureDisplayFieldValue').val(displayValue);

		$('#<portlet:namespace />' + className + 'ddmStructureFieldMessage').html(Liferay.Util.escape(message));
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />anyAssetType', 'false', '<portlet:namespace />classNamesBoxes');

	function <portlet:namespace />saveSelectBoxes() {
		var form = $(document.<portlet:namespace />fm);

		form.fm('classNameIds').val(Util.listSelect(form.fm('currentClassNameIds')));

		<%
		for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
			String className = editAssetListDisplayContext.getClassName(curRendererFactory);
		%>

			form.fm('classTypeIds<%= className %>').val(Util.listSelect(form.fm('<%= className %>currentClassTypeIds')));

		<%
		}
		%>

		submitForm(form);
	}
</aui:script>