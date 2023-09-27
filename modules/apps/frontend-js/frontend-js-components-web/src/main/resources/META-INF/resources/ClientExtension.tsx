/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef} from 'react';

interface IClientExtensionProps<T> {
	args: T;
	htmlElementBuilder: (args: T) => HTMLElement;
}

export default function ClientExtension<T>({
	args,
	htmlElementBuilder,
}: IClientExtensionProps<T>): React.ReactElement {
	const containerRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		if (containerRef.current) {
			containerRef.current.appendChild(htmlElementBuilder(args));
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return <div ref={containerRef}></div>;
}
