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

export const EMAIL_REGEX = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g;

export const FEIN_REGEX = /^\d{2}-\d{7}$/g;

export const NUMBER_REGEX = /[0-9]/g;

export const PERCENTAGE_REGEX = /^(\d)+(\.(\d)+)?%$/g;

export const PERCENTAGE_REGEX_MAX_100 = /^(?:100(?:\.00?)?|\d?\d(?:\.\d\d?)?)?%$/g;

export const PHONE_REGEX = /\((\d{3})\)\s\d{3}-\d{4}/g;

export const SANITIZE_EMPTY_KEYS_REGEX = /{{[^\s]*}}/g;

export const STATE_REGEX = /^\w{2}$/g;

export const SYMBOL_REGEX = /[^A-Za-z0-9 ]/g;

export const WEBSITE_REGEX = /[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)/g;

export const YEAR_REGEX = /^\d{4}$/g;

export const ZIP_REGEX = /[0-9]{5}/g;
