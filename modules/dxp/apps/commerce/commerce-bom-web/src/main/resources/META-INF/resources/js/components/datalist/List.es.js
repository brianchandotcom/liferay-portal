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

import HighlightedText from '../utilities/HighlightedText.es';

export function Empty() {
	return (
		<div className="commerce-datalist__option commerce-datalist__option--empty">
			{Liferay.Language.get('no-results-found')}
		</div>
	);
}

export function CheckableListElement(props) {
	return (
		<label className="commerce-datalist__option commerce-datalist__option--label">
			<input
				autoComplete="off"
				checked={props.selected}
				onChange={(e) => {
					props.updateElementState(
						e,
						{
							label: props.label,
							value: props.value,
						},
						!props.selected
					);
				}}
				type="checkbox"
				value={props.value}
			/>
			<HighlightedText query={props.query} text={props.label} />
		</label>
	);
}

export function BaseListElement(props) {
	const detaElementClasses = `commerce-datalist__option commerce-datalist__option--select${
		props.selected ? ' commerce-datalist__option--active' : ''
	}`;

	return (
		<div
			className={detaElementClasses}
			onClick={(e) =>
				props.value
					? props.updateElementState(
							e,
							{
								label: props.label,
								value: props.value,
							},
							true
					  )
					: props.resetState()
			}
			value={props.formattedValue || null}
		>
			<HighlightedText query={props.query} text={props.label || 'none'} />
		</div>
	);
}

export function ListElement(props) {
	const {checkable, ...data} = props;

	return checkable ? (
		<CheckableListElement {...data} />
	) : (
		<BaseListElement {...data} />
	);
}

export function isElementSelected(currentElValue, selectedData) {
	return selectedData.reduce(
		(acc, addedElement) => acc || currentElValue === addedElement.value,
		false
	);
}

export function List(props) {
	if (
		props.query &&
		props.data &&
		props.data.length &&
		props.filteredValues
	) {
		return props.filteredValues.length ? (
			<>
				{!props.multiselect && (
					<BaseListElement resetState={props.resetState} />
				)}
				{props.filteredValues.map((filteredValue) => {
					return props.data
						.filter(
							(dataElement) => dataElement.value === filteredValue
						)
						.map((dataElement, i) => (
							<ListElement
								checkable={!!props.multiselect}
								key={i}
								label={dataElement.label}
								name={props.name}
								query={props.query}
								selected={isElementSelected(
									dataElement.value,
									props.selectedData
								)}
								updateElementState={props.updateElementState}
								value={dataElement.value}
							/>
						));
				})}
			</>
		) : (
			<Empty />
		);
	}

	return props.data && props.data.length ? (
		<>
			{!props.multiselect && (
				<BaseListElement resetState={props.resetState} />
			)}
			{props.data.map((dataElement, i) => (
				<ListElement
					checkable={!!props.multiselect}
					key={i}
					label={dataElement.label}
					name={props.name}
					query={props.query}
					selected={isElementSelected(
						dataElement.value,
						props.selectedData
					)}
					updateElementState={props.updateElementState}
					value={dataElement.value}
				/>
			))}
		</>
	) : (
		<Empty />
	);
}

export default List;
