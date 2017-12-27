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

<blockquote><p>A table is a specific pattern for comparing datasets in a very direct an analytical way.</p></blockquote>

<%
List<Map<String, Object>> schema = new ArrayList<>();

Map<String, Object> titleField = new HashMap<>();

schema.add(titleField);

titleField.put("fieldName", "title");
titleField.put("label", "Title");
titleField.put("sortable", true);
titleField.put("sortingOrder", "asc");

Map<String, Object> releaseDateField = new HashMap<>();

schema.add(releaseDateField);

releaseDateField.put("contentRenderer", "number");
releaseDateField.put("fieldName", "releaseDate");
releaseDateField.put("label", "Release Date");

Map<String, Object> statusField = new HashMap<>();

schema.add(statusField);

statusField.put("contentRenderer", "label");
statusField.put("fieldName", "status");
statusField.put("label", "Status");

Map<String, Object> labelStylesMap = new HashMap<>();

statusField.put("labelStylesMap", labelStylesMap);

labelStylesMap.put("Watched", "success");
labelStylesMap.put("Pending", "warning");
labelStylesMap.put("*", "danger");

Map<String, Object> downloadField = new HashMap<>();

schema.add(downloadField);

downloadField.put("contentRenderer", "button");
downloadField.put("fieldName", "downloadHref");
downloadField.put("label", "Download");

Map<String, Object> ratingField = new HashMap<>();

schema.add(ratingField);

ratingField.put("contentRenderer", "number");
ratingField.put("fieldName", "rating");
ratingField.put("label", "Rating");
ratingField.put("maxValue", "100");
ratingField.put("minValue", "0");

List<Map<String, Object>> actionItems = new ArrayList<>();

Map<String, Object> actionItem1 = new HashMap<>();

actionItems.add(actionItem1);

actionItem1.put("href", "#1");
actionItem1.put("icon", "trash");
actionItem1.put("label", "Remove");
actionItem1.put("quickAction", true);

Map<String, Object> actionItem2 = new HashMap<>();

actionItems.add(actionItem2);

actionItem2.put("href", "#2");
actionItem2.put("icon", "download");
actionItem2.put("label", "Download");
actionItem2.put("quickAction", true);
actionItem2.put("separator", true);

Map<String, Object> actionItem3 = new HashMap<>();

actionItems.add(actionItem3);

actionItem3.put("href", "#3");
actionItem3.put("icon", "circle-open");
actionItem3.put("label", "Mark as watched");
actionItem3.put("quickAction", true);

List<Map<String, Object>> items = new ArrayList<>();

Map<String, Object> item1 = new HashMap<>();

items.add(item1);

item1.put("actionItems", actionItems);
item1.put("director", "Director1");
item1.put("downloadHref", "#");
item1.put("rating", "10");
item1.put("releaseDate", "May 19th 1999");
item1.put("status", "Error");
item1.put("title", "Episode I: Movie Title I");

Map<String, Object> item2 = new HashMap<>();

items.add(item2);

item2.put("actionItems", actionItems);
item2.put("director", "Director2");
item2.put("downloadHref", "#");
item2.put("rating", "50");
item2.put("releaseDate", "May 12th 2002");
item2.put("status", "Watched");
item2.put("title", "Episode II: Movie Title II");

Map<String, Object> item3 = new HashMap<>();

items.add(item3);

item3.put("actionItems", actionItems);
item3.put("director", "Director1");
item3.put("downloadHref", "#");
item3.put("rating", "60");
item3.put("releaseDate", "May 12th 2005");
item3.put("status", "Watched");
item3.put("title", "Movie Title III");

Map<String, Object> item4 = new HashMap<>();

items.add(item4);

item4.put("actionItems", actionItems);
item4.put("director", "Director1");
item4.put("downloadHref", "#");
item4.put("rating", "90");
item4.put("releaseDate", "May 25th 1977");
item4.put("status", "Error");
item4.put("title", "Episode IV: Movie Title IV");

Map<String, Object> item5 = new HashMap<>();

items.add(item5);

item5.put("actionItems", actionItems);
item5.put("director", "Director2");
item5.put("downloadHref", "#");
item5.put("rating", "100");
item5.put("releaseDate", "May 21st 1980");
item5.put("status", "Watched");
item5.put("title", "Episode V: Movie Title V");

Map<String, Object> item6 = new HashMap<>();

items.add(item6);

item6.put("actionItems", actionItems);
item6.put("director", "Director1");
item6.put("downloadHref", "#");
item6.put("rating", "90");
item6.put("releaseDate", "May 25th 1983");
item6.put("status", "Watched");
item6.put("title", "Movie Title VI");

System.out.println(items);
%>

<clay:table
    items="<%= items %>"
    schema="<%= schema %>"
    showActionsMenu="<%= true %>"
/>