/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Drills down an object using the path
 * @param path : string
 * @param item : any
 * @returns The part of the item indicated by the path
 *
 * path: 'a.b.c'
 * item: {a: {b: {c: [1, 2, 3]}}}
 * returns: [1, 2, 3]
 *
 * path: 'a.b*'
 * item: {a: {b: {c: [1, 2, 3]}}}
 * returns: {c: [1, 2, 3]}
 *
 * path: 'a[].b.c'
 * item: {a: [{b: {c: 1, d: 3}}, {b: {c: 2, d: 3}}, {b: {c: 3, d: 3}}]}
 * returns: [{c:1}, {c: 2}, {c: 3}]
 */
export declare const getItemField: (path: string, item: any) => any;
