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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {navigate} from 'frontend-js-web';
import React from 'react';

import '../css/AddFDSView.scss';

const RequiredMark = () => (
	<>
		<span className="inline-item-after reference-mark text-warning">
			<ClayIcon symbol="asterisk" />
		</span>
		<span className="hide-accessible sr-only">
			{Liferay.Language.get('required')}
		</span>
	</>
);

type HeadlessResource = {
	bundleLabel: string;
	entityClassName: string;
	name: string;
	version: string;
};
interface IFDSViewsProps {
	fdsViewsURL: string;
	headlessResources: Array<HeadlessResource>;
	namespace: string;
}

const AddFDSView = ({
	fdsViewsURL,
	headlessResources,
	namespace,
}: IFDSViewsProps) => {
	return (
		<ClayLayout.ContainerFluid formSize="lg">
			<ClayForm>
				<ClayLayout.Sheet className="pt-4" size="lg">
					<ClayLayout.SheetHeader>
						<h2 className="sheet-title">
							{Liferay.Language.get('new-dataset')}
						</h2>
					</ClayLayout.SheetHeader>

					<ClayLayout.SheetSection>
						<ClayForm.Group>
							<label htmlFor={`${namespace}fdsViewNameInput`}>
								{Liferay.Language.get('name')}

								<RequiredMark />
							</label>

							<ClayInput
								id={`${namespace}fdsViewNameInput`}
								type="text"
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label
								htmlFor={`${namespace}fdsViewProviderAutocomplete`}
								id={`${namespace}fdsViewProviderAutocompleteLabel`}
							>
								{Liferay.Language.get('provider')}

								<RequiredMark />
							</label>

							<ClayAutocomplete
								aria-labelledby={`${namespace}fdsViewProviderAutocompleteLabel`}
								id={`${namespace}fdsViewProviderAutocomplete`}
								menuTrigger="focus"
								messages={{
									loading: '',
									notFound: Liferay.Language.get(
										'no-results-found'
									),
								}}
								placeholder={Liferay.Language.get(
									'choose-an-option'
								)}
							>
								{headlessResources.map((headlessResource) => (
									<ClayAutocomplete.Item
										key={headlessResource.entityClassName}
									>
										{`${headlessResource.name} (${headlessResource.version} - ${headlessResource.bundleLabel})`}
									</ClayAutocomplete.Item>
								))}
							</ClayAutocomplete>
						</ClayForm.Group>
					</ClayLayout.SheetSection>

					<ClayLayout.SheetFooter>
						<ClayButton.Group spaced>
							<ClayButton>
								{Liferay.Language.get('save')}
							</ClayButton>

							<ClayButton
								displayType="secondary"
								onClick={() => {
									navigate(fdsViewsURL);
								}}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayButton.Group>
					</ClayLayout.SheetFooter>
				</ClayLayout.Sheet>
			</ClayForm>
		</ClayLayout.ContainerFluid>
	);
};

export default AddFDSView;
