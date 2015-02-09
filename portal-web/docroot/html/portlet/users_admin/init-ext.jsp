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

<%
List<Map<String,Object>> userFields = new ArrayList<Map<String, Object>>();

String[] userNameFields = StringUtil.split(LanguageUtil.get(locale, "user.name.fields"));
String[] userNameRequiredFields = StringUtil.split(LanguageUtil.get(locale, "user.name.required.fields"));

List userNameRequriedFieldsList = Arrays.asList(userNameRequiredFields);

for (String userNameField: userNameFields) {
	Map<String, Object> userFieldMap = new HashMap<String, Object>();

	userFieldMap.put("name", userNameField);

	boolean required = userNameRequriedFieldsList.contains(userNameField);

	userFieldMap.put("required", required);

	String testOptionsProperty = "user.name." + userNameField + ".options";

	String optionsString = LanguageUtil.get(locale, testOptionsProperty);

	if (!testOptionsProperty.equals(optionsString)) {
		userFieldMap.put("options", StringUtil.split(optionsString));
	}

	userFields.add(userFieldMap);
}
%>