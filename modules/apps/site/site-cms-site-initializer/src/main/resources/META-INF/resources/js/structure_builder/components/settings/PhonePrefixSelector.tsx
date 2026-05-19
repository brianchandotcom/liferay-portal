/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {getFlagSymbol} from '@liferay/object-js-components-web';
import React from 'react';

import {config} from '../../config';
import {Country} from '../../types/Country';

const Trigger = React.forwardRef(
	(
		{
			id,
			selectedCountry,
			...otherProps
		}: {
			id?: string;
			selectedCountry?: Country;
		},
		ref: React.Ref<HTMLButtonElement>
	) => {
		return (
			<button
				{...otherProps}
				className="form-control form-control-select form-control-select-secondary form-control-shrink pr-5"
				id={id}
				ref={ref}
				type="button"
			>
				{selectedCountry && (
					<>
						<ClayIcon
							className="inline-item inline-item-before"
							symbol={getFlagSymbol(selectedCountry.a2)}
						/>

						{`+${selectedCountry.idd}`}
					</>
				)}
			</button>
		);
	}
);

export default function PhonePrefixSelector({
	disabled,
	id,
	onChange,
	value,
}: {
	disabled?: boolean;
	id?: string;
	onChange: (countryCode: string) => void;
	value?: string;
}) {
	const countries = config.countries;

	const selectedCountry = countries.find((country) => country.a2 === value);

	return (
		<Picker
			as={Trigger}
			disabled={disabled}
			id={id}
			items={countries}
			messages={{
				itemDescribedby: Liferay.Language.get(
					'you-are-currently-on-a-text-element,-inside-of-a-list-box'
				),
				itemSelected: Liferay.Language.get('x-selected'),
				scrollToBottomAriaLabel:
					Liferay.Language.get('scroll-to-bottom'),
				scrollToTopAriaLabel: Liferay.Language.get('scroll-to-top'),
			}}
			onSelectionChange={(selectedKey: React.Key) => {
				onChange(selectedKey as string);
			}}
			selectedCountry={selectedCountry}
			selectedKey={value}
		>
			{({a2, idd, name}: Country) => (
				<Option key={a2} textValue={`+${idd} ${name}`}>
					<ClayLayout.ContentRow containerElement="span">
						<ClayLayout.ContentCol containerElement="span">
							<ClayLayout.ContentSection>
								<ClayIcon
									className="inline-item inline-item-before"
									symbol={getFlagSymbol(a2)}
								/>

								<span
									className="d-inline-block"
									style={{minWidth: '3.5rem'}}
								>
									{`+${idd}`}
								</span>
							</ClayLayout.ContentSection>
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol containerElement="span" expand>
							<ClayLayout.ContentSection>
								<span className="pr-3 text-secondary">
									{name}
								</span>
							</ClayLayout.ContentSection>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				</Option>
			)}
		</Picker>
	);
}
