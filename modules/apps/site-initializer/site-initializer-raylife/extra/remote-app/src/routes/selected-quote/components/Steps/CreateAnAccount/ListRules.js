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

import {RuleIcon} from '../CreateAnAccount/RuleIcon';

export function ListRules({objValidate}) {
	return (
		<div className="mt-3">
			<ul className="list-unstyled">
				<li>
					<RuleIcon
						label={
							<>
								At least <b>8 characters</b>
							</>
						}
						status={objValidate.qtdCharacter}
					/>
				</li>

				<li>
					<RuleIcon
						label={
							<>
								At least <b>5 unique characters</b>
							</>
						}
						status={objValidate.uniqueCharacter}
					/>
				</li>

				<li>
					<RuleIcon
						label={
							<>
								At least <b>1 symbol</b>
							</>
						}
						status={objValidate.symbolCharacter}
					/>
				</li>

				<li>
					<RuleIcon
						label={
							<>
								At least <b>1 number</b>
							</>
						}
						status={objValidate.numberCharacter}
					/>
				</li>

				<li>
					<RuleIcon
						label={
							<>
								<b>No spaces</b> allowed
							</>
						}
						status={objValidate.noSpace}
					/>
				</li>

				<li>
					<RuleIcon
						label="Passwords are the same"
						status={objValidate.samePassword}
					/>
				</li>
			</ul>
		</div>
	);
}
