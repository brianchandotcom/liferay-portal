/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export function getTooltipTitles(title) {
	const activationNames = title?.split(',');

	return (
		!!activationNames.length && (
			<div>
				<p className="font-weight-bold m-0">{activationNames[0]}</p>

				{activationNames.length > 1 && (
					<p className="font-weight-normal m-0 text-paragraph-sm">
						{activationNames[1]}
					</p>
				)}
			</div>
		)
	);
}
