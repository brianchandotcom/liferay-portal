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

 function getCookie(name) {
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookies = decodedCookie.split(';');
		name = name + '=';

    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        if (!cookie.indexOf(name)) {
            return cookie.substring(name.length, cookie.length);
        }
    }
}

const applicationIdCookie = getCookie(nameCookieId);

if (applicationIdCookie) {
    document.getElementById('content-agent-text-your-application').textContent =
        'Your Application #' + applicationIdCookie;
}