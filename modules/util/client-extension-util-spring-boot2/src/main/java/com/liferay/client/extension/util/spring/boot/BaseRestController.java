/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot;

import com.liferay.client.extension.util.spring.boot.service.BaseService;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author Brian Wing Shun Chan
 */
@RestController
public abstract class BaseRestController extends BaseService {
}