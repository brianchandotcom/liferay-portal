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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/frontend-data-set" prefix="frontend-data-set" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.list.type.model.ListTypeDefinition" %><%@
page import="com.liferay.list.type.model.ListTypeEntry" %><%@
page import="com.liferay.object.constants.ObjectRelationshipConstants" %><%@
page import="com.liferay.object.exception.ObjectDefinitionActiveException" %><%@
page import="com.liferay.object.exception.ObjectDefinitionLabelException" %><%@
page import="com.liferay.object.exception.ObjectDefinitionNameException" %><%@
page import="com.liferay.object.exception.ObjectDefinitionPluralLabelException" %><%@
page import="com.liferay.object.exception.ObjectDefinitionScopeException" %><%@
page import="com.liferay.object.exception.ObjectDefinitionStatusException" %><%@
page import="com.liferay.object.exception.RequiredObjectFieldException" %><%@
page import="com.liferay.object.model.ObjectAction" %><%@
page import="com.liferay.object.model.ObjectDefinition" %><%@
page import="com.liferay.object.model.ObjectEntry" %><%@
page import="com.liferay.object.model.ObjectField" %><%@
page import="com.liferay.object.model.ObjectLayout" %><%@
page import="com.liferay.object.model.ObjectLayoutTab" %><%@
page import="com.liferay.object.model.ObjectRelationship" %><%@
page import="com.liferay.object.model.ObjectView" %><%@
page import="com.liferay.object.scope.ObjectScopeProvider" %><%@
page import="com.liferay.object.web.internal.constants.ObjectWebKeys" %><%@
page import="com.liferay.object.web.internal.list.type.constants.ListTypeFDSNames" %><%@
page import="com.liferay.object.web.internal.list.type.display.context.ViewListTypeDefinitionsDisplayContext" %><%@
page import="com.liferay.object.web.internal.list.type.display.context.ViewListTypeEntriesDisplayContext" %><%@
page import="com.liferay.object.web.internal.object.definitions.constants.ObjectDefinitionsFDSNames" %><%@
page import="com.liferay.object.web.internal.object.definitions.constants.ObjectDefinitionsScreenNavigationEntryConstants" %><%@
page import="com.liferay.object.web.internal.object.definitions.display.context.ObjectDefinitionsActionsDisplayContext" %><%@
page import="com.liferay.object.web.internal.object.definitions.display.context.ObjectDefinitionsDetailsDisplayContext" %><%@
page import="com.liferay.object.web.internal.object.definitions.display.context.ObjectDefinitionsFieldsDisplayContext" %><%@
page import="com.liferay.object.web.internal.object.definitions.display.context.ObjectDefinitionsLayoutsDisplayContext" %><%@
page import="com.liferay.object.web.internal.object.definitions.display.context.ObjectDefinitionsRelationshipsDisplayContext" %><%@
page import="com.liferay.object.web.internal.object.definitions.display.context.ObjectDefinitionsViewsDisplayContext" %><%@
page import="com.liferay.object.web.internal.object.definitions.display.context.ViewObjectDefinitionsDisplayContext" %><%@
page import="com.liferay.object.web.internal.object.entries.constants.ObjectEntriesFDSNames" %><%@
page import="com.liferay.object.web.internal.object.entries.display.context.ObjectEntryDisplayContext" %><%@
page import="com.liferay.object.web.internal.object.entries.display.context.ViewObjectEntriesDisplayContext" %><%@
page import="com.liferay.petra.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.LayoutConstants" %><%@
page import="com.liferay.portal.kernel.model.ModelHintsConstants" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.util.PropsUtil" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Objects" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />