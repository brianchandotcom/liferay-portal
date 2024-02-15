/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export declare function onDiscardDraft(
	event: React.MouseEvent<HTMLButtonElement, MouseEvent>,
	form?: HTMLFormElement
): void;
export declare function useDisabledDiscardDraft(): boolean;
export default function DiscardDraftButton(): JSX.Element;
