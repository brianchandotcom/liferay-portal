/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const tokens = location.href.split('/');
const groupKey = tokens[tokens.length - 2];

const link = document.getElementById('homeLink');

link.href = link.href.replace('[$GROUP_KEY$]', groupKey);
