/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Share} from 'lucide-react';

import {Button} from '../ui/button';

export default function ProductQuickActions() {
	const handleShare = async () => {
		try {
			const url = window.location.href;

			await navigator.clipboard.writeText(url);

			alert(
				'Link copied!\nThe page URL has been copied to your clipboard.'
			);
		}
		catch {
			alert('Copy FAILED!\nWe couldn’t copy the link. Try again.');
		}
	};

	return (
		<Button
			className="justify-start w-full"
			onClick={handleShare}
			size="sm"
			variant="outline"
		>
			<Share className="h-2 mr-1 w-2" />
			Copy & Share
		</Button>
	);
}
