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

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import React, {useState} from 'react';

const CharacterRow = ({characters, description}) => (
	<div className="mb-2">
		{characters.map((character, index) => (
			<div className="c-kbd c-kbd-light mr-1" key={index}>
				{character}
			</div>
		))}

		<span className="ddm-field-text-small ml-1">{description}</span>
	</div>
);

const HelpTextContent = () => (
	<>
		<CharacterRow
			characters={['9']}
			description={Liferay.Language.get(
				'user-must-enter-a-numeric-digit'
			)}
		/>
		<CharacterRow
			characters={['0']}
			description={Liferay.Language.get('user-may-enter-a-numeric-digit')}
		/>
		<CharacterRow
			characters={['ABC']}
			description={Liferay.Language.get('any-input-mask-character')}
		/>
		<CharacterRow
			characters={['Space', '-', '/', ':', ',', '.']}
			description={Liferay.Language.get('separators')}
		/>
		<CharacterRow
			characters={['(', ')', '[', ']', '{', '}']}
			description={Liferay.Language.get('group-separators')}
		/>
		<CharacterRow
			characters={['#', '$', '%', '+']}
			description={Liferay.Language.get('prefix-and-suffix-symbols')}
		/>
	</>
);

const HelpText = ({label, visible}) => {
	const [showMore, setShowMore] = useState(false);

	return (
		<div className={classNames('form-group', {hide: !visible})}>
			<div className="align-items-center d-flex justify-content-between mb-2">
				<label>{label}</label>

				<ClayButton
					borderless
					displayType="secondary"
					onClick={() => setShowMore(!showMore)}
					small
				>
					{showMore
						? Liferay.Language.get('show-less')
						: Liferay.Language.get('show-more')}
				</ClayButton>
			</div>

			{showMore && <HelpTextContent />}
		</div>
	);
};

export default HelpText;
