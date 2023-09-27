/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const EMAIL_REGEX = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g;

export const FEIN_REGEX = /^\d{2}-\d{7}$/g;

export const NUMBER_REGEX = /[0-9]/g;

export const PERCENTAGE_REGEX = /^(\d)+(\.(\d)+)?%$/g;

export const PERCENTAGE_REGEX_MAX_100 = /^(?:100(?:\.00?)?|\d?\d(?:\.\d\d?)?)?%$/g;

export const PHONE_REGEX = /\((\d{3})\)\s\d{3}-\d{4}/g;

export const STATE_REGEX = /^\w{2}$/g;

export const SYMBOL_REGEX = /[^A-Za-z0-9 ]/g;

export const WEBSITE_REGEX = /[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)/g;

export const YEAR_REGEX = /^\d{4}$/g;

export const ZIP_REGEX = /[0-9]{5}/g;
