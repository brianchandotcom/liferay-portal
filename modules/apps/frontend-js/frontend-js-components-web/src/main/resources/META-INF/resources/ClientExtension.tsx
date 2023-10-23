/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useEffect, useRef} from 'react';

export interface IHTMLElementBuilder<T> {
	(args: T): HTMLElement;
}

interface IClientExtensionProps<T> {
	args: T;
	htmlElementBuilder?: IHTMLElementBuilder<T>;
}

export default function ClientExtension<T>({
	args,
	htmlElementBuilder,
}: IClientExtensionProps<T>): React.ReactElement {
	const containerRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		const {current} = containerRef;

		if (current && htmlElementBuilder) {
			while (current.firstChild) {
				current.removeChild(current.firstChild);
			}

			try {
				current.appendChild(htmlElementBuilder(args));
			}
			catch (error) {
				console.error(
					'The client extension implemented by the function',
					htmlElementBuilder,
					'caused an error when trying to render its HTML content.',
					'Please fix your client extension.',
					error
				);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [htmlElementBuilder]);

	return htmlElementBuilder ? (
		<div ref={containerRef}></div>
	) : (
		<ClayLoadingIndicator />
	);
}
