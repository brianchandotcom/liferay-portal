/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

'use client';

import clsx from 'clsx';

export default function PreviewHTML({
	className,
	content,
}: {
	className?: string;
	content: string;
}) {
	return (
		<div
			className={clsx('flex flex-col gap-4 text-justify', className)}
			dangerouslySetInnerHTML={{
				__html: content,
			}}
		/>
	);
}
