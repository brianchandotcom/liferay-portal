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

<aui:script require="metal-dom/src/dom as dom">
	var Util = Liferay.Util;

	var MAP_DDM_STRUCTURES = {};

	var assetMultipleSelector = document.getElementById(
		'<portlet:namespace />currentClassNameIds'
	);
	var assetSelector = document.getElementById(
		'<portlet:namespace />anyAssetType'
	);
	var orderByColumn1 = document.getElementById(
		'<portlet:namespace />orderByColumn1'
	);
	var orderByColumn2 = document.getElementById(
		'<portlet:namespace />orderByColumn2'
	);
	var orderingPanel = document.getElementById('<portlet:namespace />ordering');
	var sourcePanel = document.querySelector('.source-container');

	<%
	for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
		String className = editAssetListDisplayContext.getClassName(curRendererFactory);
	%>

		Util.toggleSelectBox(
			'<portlet:namespace />anyClassType<%= className %>',
			'false',
			'<portlet:namespace /><%= className %>Boxes'
		);

		var <%= className %>Options = document.getElementById(
			'<portlet:namespace /><%= className %>Options'
		);

		function <portlet:namespace />toggle<%= className %>(removeOrderBySubtype) {
			var assetOptions = assetMultipleSelector.options;

			var showOptions =
				assetSelector.value == '<%= curRendererFactory.getClassNameId() %>' ||
				(assetSelector.value == 'false' &&
					assetOptions.length == 1 &&
					assetOptions[0].value ==
						'<%= curRendererFactory.getClassNameId() %>');

			if (showOptions) {
				<%= className %>Options.classList.remove('hide');
			}
			else {
				<%= className %>Options.classList.add('hide');
			}

			if (removeOrderBySubtype) {
				Array.prototype.forEach.call(
					orderingPanel.querySelectorAll('.order-by-subtype'),
					function(option) {
						dom.exitDocument(option);
					}
				);
			}

			<c:if test="<%= editAssetListDisplayContext.isShowSubtypeFieldsFilter() %>">
				<%= className %>toggleSubclassesFields(true);
			</c:if>
		}

		<%
		ClassTypeReader classTypeReader = curRendererFactory.getClassTypeReader();

		List<ClassType> assetAvailableClassTypes = classTypeReader.getAvailableClassTypes(editAssetListDisplayContext.getReferencedModelsGroupIds(), locale);

		if (assetAvailableClassTypes.isEmpty()) {
			continue;
		}

		for (ClassType classType : assetAvailableClassTypes) {
			List<ClassTypeField> classTypeFields = classType.getClassTypeFields();

			if (classTypeFields.isEmpty()) {
				continue;
			}
		%>

			var optgroupClose = '</optgroup>';
			var optgroupOpen =
				'<optgroup class="order-by-subtype" label="<%= HtmlUtil.escape(classType.getName()) %>">';

			var columnBuffer1 = [optgroupOpen];
			var columnBuffer2 = [optgroupOpen];

			<%
			String orderByColumn1 = editAssetListDisplayContext.getOrderByColumn1();
			String orderByColumn2 = editAssetListDisplayContext.getOrderByColumn2();

			for (ClassTypeField classTypeField : classTypeFields) {
				String value = editAssetListDisplayContext.encodeName(classTypeField.getClassTypeId(), classTypeField.getName(), null);
				String selectedOrderByColumn1 = StringPool.BLANK;
				String selectedOrderByColumn2 = StringPool.BLANK;

				if (orderByColumn1.equals(value)) {
					selectedOrderByColumn1 = "selected";
				}

				if (orderByColumn2.equals(value)) {
					selectedOrderByColumn2 = "selected";
				}
			%>

				columnBuffer1.push(
					'<option <%= selectedOrderByColumn1 %> value="<%= value %>"><%= HtmlUtil.escapeJS(classTypeField.getLabel()) %></option>'
				);
				columnBuffer2.push(
					'<option <%= selectedOrderByColumn2 %> value="<%= value %>"><%= HtmlUtil.escapeJS(classTypeField.getLabel()) %></option>'
				);

			<%
			}
			%>

			columnBuffer1.push(optgroupClose);
			columnBuffer2.push(optgroupClose);

			MAP_DDM_STRUCTURES[
				'<%= className %>_<%= classType.getClassTypeId() %>_optTextOrderByColumn1'
			] = columnBuffer1.join('');
			MAP_DDM_STRUCTURES[
				'<%= className %>_<%= classType.getClassTypeId() %>_optTextOrderByColumn2'
			] = columnBuffer2.join('');

		<%
		}
		%>

		var <%= className %>SubtypeSelector = document.getElementById(
			'<portlet:namespace />anyClassType<%= className %>'
		);

		<c:if test="<%= editAssetListDisplayContext.isShowSubtypeFieldsFilter() %>">
			function <%= className %>toggleSubclassesFields(
				hideSubtypeFilterEnableWrapper
			) {
				var selectedSubtype = <%= className %>SubtypeSelector.value;

				var structureOptions = document.getElementById(
					'<portlet:namespace />' + selectedSubtype + '_<%= className %>Options'
				);

				if (structureOptions) {
					structureOptions.classList.remove('hide');
				}

				var subtypeFieldsWrappers = document.querySelectorAll(
					'#<portlet:namespace /><%= className %>subtypeFieldsWrapper, #<portlet:namespace /><%= className %>subtypeFieldsFilterEnableWrapper'
				);

				Array.prototype.forEach.call(subtypeFieldsWrappers, function(
					subtypeFieldsWrapper
				) {
					if (selectedSubtype != 'false' && selectedSubtype != 'true') {
						Array.prototype.forEach.call(
							orderingPanel.querySelectorAll('.order-by-subtype'),
							function(option) {
								dom.exitDocument(option);
							}
						);

						var optTextOrderByColumn1 =
							MAP_DDM_STRUCTURES[
								'<%= className %>_' +
									selectedSubtype +
									'_optTextOrderByColumn1'
							];

						if (optTextOrderByColumn1) {
							dom.append(orderByColumn1, optTextOrderByColumn1);
						}

						var optTextOrderByColumn2 =
							MAP_DDM_STRUCTURES[
								'<%= className %>_' +
									selectedSubtype +
									'_optTextOrderByColumn2'
							];

						if (optTextOrderByColumn2) {
							dom.append(orderByColumn2, optTextOrderByColumn2);
						}

						if (structureOptions) {
							subtypeFieldsWrapper.classList.remove('hide');
						}
						else if (hideSubtypeFilterEnableWrapper) {
							subtypeFieldsWrapper.classList.add('hide');
						}
					}
					else if (hideSubtypeFilterEnableWrapper) {
						subtypeFieldsWrapper.classList.add('hide');
					}
				});
			}

			<%= className %>toggleSubclassesFields(false);

			<%= className %>SubtypeSelector.addEventListener('change', function(event) {
				setDDMFields('<%= className %>', '', '', '', '');

				var subtypeFieldsFilterEnabledCheckbox = document.getElementById(
					'<portlet:namespace />subtypeFieldsFilterEnabled<%= className %>'
				);

				if (subtypeFieldsFilterEnabledCheckbox) {
					subtypeFieldsFilterEnabledCheckbox.checked = false;
				}

				var assetSubtypeFields = sourcePanel.querySelectorAll(
					'.asset-subtypefields'
				);

				Array.prototype.forEach.call(assetSubtypeFields, function(
					assetSubtypeField
				) {
					assetSubtypeField.classList.add('hide');
				});

				<%= className %>toggleSubclassesFields(true);
			});
		</c:if>

	<%
	}
	%>

	function <portlet:namespace />toggleSubclasses(removeOrderBySubtype) {

		<%
		for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
			String className = editAssetListDisplayContext.getClassName(curRendererFactory);
		%>

			<portlet:namespace />toggle<%= className %>(removeOrderBySubtype);

		<%
		}
		%>

	}

	<portlet:namespace />toggleSubclasses(false);

	var ddmStructureFieldNameInput = document.getElementById(
		'<portlet:namespace />ddmStructureFieldName'
	);
	var ddmStructureFieldValueInput = document.getElementById(
		'<portlet:namespace />ddmStructureFieldValue'
	);

	if (
		assetSelector &&
		ddmStructureFieldNameInput &&
		ddmStructureFieldValueInput
	) {
		assetSelector.addEventListener('change', function(event) {
			ddmStructureFieldNameInput.value = '';
			ddmStructureFieldValueInput.value = '';

			<portlet:namespace />toggleSubclasses(true);
		});
	}

	dom.delegate(
		sourcePanel,
		'click',
		'.asset-subtypefields-wrapper-enable label',
		function(event) {
			var subtypeFieldsFilterEnabledInput = event.delegateTarget.querySelector(
				'input'
			);

			var assetSubtypefieldsPopupButtons = document.querySelectorAll(
				'.asset-subtypefields-popup .btn'
			);

			if (subtypeFieldsFilterEnabledInput) {
				Array.prototype.forEach.call(
					assetSubtypefieldsPopupButtons,
					function(assetSubtypefieldsPopupButton) {
						Util.toggleDisabled(
							assetSubtypefieldsPopupButton,
							!subtypeFieldsFilterEnabledInput.checked
						);
					}
				);
			}
		}
	);

	Liferay.after('inputmoveboxes:moveItem', function(event) {
		if (
			event.fromBox.attr('id') ==
				'<portlet:namespace />currentClassNameIds' ||
			event.toBox.attr('id') == '<portlet:namespace />currentClassNameIds'
		) {
			<portlet:namespace />toggleSubclasses();
		}
	});

	var ddmStructureDisplayFieldValueInput = document.getElementById(
		'<portlet:namespace />ddmStructureDisplayFieldValue'
	);

	dom.delegate(sourcePanel, 'click', '.asset-subtypefields-popup', function(
		event
	) {
		var delegateTarget = event.delegateTarget;

		var btn = delegateTarget.querySelector('.btn');

		var uri = btn.dataset.href;

		uri = Util.addParams(
			'<portlet:namespace />ddmStructureDisplayFieldValue=' +
				encodeURIComponent(ddmStructureDisplayFieldValueInput.value),
			uri
		);
		uri = Util.addParams(
			'<portlet:namespace />ddmStructureFieldName=' +
				encodeURIComponent(ddmStructureFieldNameInput.value),
			uri
		);
		uri = Util.addParams(
			'<portlet:namespace />ddmStructureFieldValue=' +
				encodeURIComponent(ddmStructureFieldValueInput.value),
			uri
		);

		Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true
				},
				eventName: '<portlet:namespace />selectDDMStructureField',
				id: '<portlet:namespace />selectDDMStructure' + delegateTarget.id,
				title:
					'<liferay-ui:message arguments="structure-field" key="select-x" />',
				uri: uri
			},
			function(event) {
				setDDMFields(
					event.className,
					event.name,
					event.value,
					event.displayValue,
					event.label + ': ' + event.displayValue
				);
			}
		);
	});

	function setDDMFields(className, name, value, displayValue, message) {
		ddmStructureFieldNameInput.value = name;
		ddmStructureFieldValueInput.value = value;
		ddmStructureDisplayFieldValueInput.value = displayValue;

		var ddmStructureFieldMessageContainer = document.getElementById(
			'<portlet:namespace />' + className + 'ddmStructureFieldMessage'
		);

		if (ddmStructureFieldMessageContainer) {
			ddmStructureFieldMessageContainer.innerHTML = Liferay.Util.escape(
				message
			);
		}
	}

	Util.toggleSelectBox(
		'<portlet:namespace />anyAssetType',
		'false',
		'<portlet:namespace />classNamesBoxes'
	);
</aui:script>