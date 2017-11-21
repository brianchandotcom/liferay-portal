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

<blockquote><p>Labels are a mechanism to categorize information providing quick recognition.</p></blockquote>

<div class="row mb-3">
    <div class="col-12 d-inline-flex">
        <clay:label label="Label text" style="info" />
        <clay:label label="Status" />
        <clay:label label="Pending" style="warning" />
        <clay:label label="Rejected" style="danger" />
        <clay:label label="Approved" style="success" />
    </div>

    <div class="col-12 d-inline-flex">
        <clay:label label="Label text" size="lg" style="info" />
        <clay:label label="Status" size="lg" />
        <clay:label label="Pending" size="lg" style="warning" />
        <clay:label label="Rejected" size="lg" style="danger" />
        <clay:label label="Approved" size="lg" style="success" />
    </div>
</div>

<h3>LABEL REMOVABLE</h3>

<div class="row mb-3">
    <div class="col-12 d-inline-flex">
        <clay:label closeable="<%= true %>" label="Normal Label" />
        <clay:label closeable="<%= true %>" label="Large Label" size="lg" style="success" />
    </div>
</div>

<h3>LABEL WITH LINK</h3>

<div class="row">
    <div class="col-12">
        <clay:label href="#" label="Label Text" />
    </div>
</div>