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

import Form from '..';
import {TypedDocumentNode} from '@apollo/client';

import i18n from '../../../i18n';
import {AutoCompleteProps} from '../AutoComplete';

type RenderedFieldOptions = string[] | {label: string; value: string}[];

export type RendererFields = {
	label: string;
	name: string;
	options?: RenderedFieldOptions;
	type: 'autocomplete' | 'checkbox' | 'text' | 'select' | 'multiselect';
} & Partial<AutoCompleteProps>;

type RendererProps = {
	fields: RendererFields[];
	filter?: string;
};

const Renderer: React.FC<RendererProps> = ({fields, filter}) => {
	const fieldsFiltered = fields.filter(({label}) =>
		filter ? label.toLowerCase().includes(filter.toLowerCase()) : true
	);

	return (
		<div>
			{fieldsFiltered.map((field, index) => {
				const {label, name, type, options = [], gqlQuery} = field;

				if (type === 'text') {
					return <Form.Input key={index} {...field} />;
				}

				if (type === 'select') {
					return (
						<Form.Select
							key={index}
							label={label}
							name={name}
							options={(options || []).map((option) =>
								typeof option === 'object'
									? option
									: {
											label: option,
											value: option,
									  }
							)}
						/>
					);
				}

				if (type === 'checkbox') {
					return (
						<div key={index}>
							<label>{label}</label>

							{options.map((option, index) => (
								<Form.Checkbox
									key={index}
									label={
										typeof option === 'string'
											? option
											: option.label
									}
								/>
							))}
						</div>
					);
				}

				if (type === 'autocomplete') {
					return (
						<Form.AutoComplete
							gqlQuery={gqlQuery as TypedDocumentNode}
							key={index}
							objectName="case"
							onSearch={() => null}
							transformData={field.transformData}
						/>
					);
				}

				if (type === 'multiselect') {
					return <Form.MultiSelect key={index} label={label} />;
				}

				return null;
			})}

			{!fieldsFiltered.length && (
				<p>{i18n.translate('there-are-no-matching-results')}</p>
			)}
		</div>
	);
};

export default Renderer;
