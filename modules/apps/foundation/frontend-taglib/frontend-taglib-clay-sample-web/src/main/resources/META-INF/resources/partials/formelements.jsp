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

<h3>CHECKBOX</h3>

<blockquote><p>A checkbox is a component that allows the user selecting something written in its associated text label. A list of consecutive checkboxes would allow the user to select multiple things.</p></blockquote>

<table class="table">
    <thead>
        <tr>
            <th>STATE</th>
            <th>DEFINITION</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><clay:checkbox checked="<%= true %>" label="asdasd" name="name" /></td>
            <td>On</td>
        </tr>
        <tr>
            <td><clay:checkbox label="asdas" name="name" /></td>
            <td>Off</td>
        </tr>
        <tr>
            <td><clay:checkbox checked="<%= true %>" disabled="<%= true %>" label="asdsa" name="name" /></td>
            <td>On disabled</td>
        </tr>
        <tr>
            <td><clay:checkbox disabled="<%= true %>" label="asdasd" name="name" /></td>
            <td>Off disabled</td>
        </tr>
        <tr>
            <td><clay:checkbox indeterminate="<%= true %>" label="asdasd" name="name" /></td>
            <td>Checkbox Variable for multiple selection</td>
        </tr>
    </tbody>
</table>