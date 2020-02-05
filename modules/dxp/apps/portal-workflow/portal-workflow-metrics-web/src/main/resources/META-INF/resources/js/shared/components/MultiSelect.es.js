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

import React from 'react';

import Icon from './Icon.es';

/**
 * @class
 * @memberof shared/components
 */
export default class MultiSelect extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			active: false,
			searchKey: '',
			verticalIndex: -1
		};
	}

	componentDidMount() {
		document.addEventListener(
			'mousedown',
			this.handleClickOutside.bind(this)
		);
	}

	componentWillUnmount() {
		document.removeEventListener(
			'mousedown',
			this.handleClickOutside.bind(this)
		);
	}

	addTag(tagId) {
		const {onChangeTags, selectedTagsId} = this.props;

		this.setState(
			{
				searchKey: '',
				verticalIndex: -1
			},
			() => {
				if (onChangeTags) {
					onChangeTags(selectedTagsId.concat([tagId]));
				}
				this.inputRef.focus();
			}
		);
	}

	cancelDropList() {
		const event = new Event('mousedown', {bubbles: true});

		this.wrapperRef.dispatchEvent(event);
	}

	changeSearch(event) {
		const {
			target: {value: searchKey}
		} = event;

		this.setState({
			searchKey,
			verticalIndex: -1
		});
	}

	get dataFiltered() {
		const {data, fieldId = 'id', selectedTagsId} = this.props;
		const {searchKey} = this.state;

		const term = searchKey.toLowerCase().trim();

		return data
			.filter(node => !selectedTagsId.includes(`${node[fieldId]}`))
			.filter(
				({desc}) => term === '' || desc.toLowerCase().indexOf(term) > -1
			);
	}

	getSelectedTags() {
		const {data, fieldId = 'id', selectedTagsId} = this.props;

		return data.filter(node => selectedTagsId.includes(`${node[fieldId]}`));
	}

	handleClickOutside(event) {
		if (this.wrapperRef && !this.wrapperRef.contains(event.target)) {
			this.hideDropList();
		}
	}

	hideDropList() {
		this.setState({active: false, highlighted: false});
	}

	onSearch(event) {
		const dataFiltered = this.dataFiltered;
		const {fieldId = 'id'} = this.props;
		const keyArrowDown = 38;
		const keyArrowUp = 40;
		const {keyCode} = event;
		const keyEnter = 13;
		const keyEscape = 27;
		const keyTab = 9;
		let {verticalIndex} = this.state;

		if ([keyArrowDown, keyArrowUp].includes(keyCode)) {
			const dataCount = dataFiltered.length;

			verticalIndex =
				keyCode === keyArrowUp ? verticalIndex + 1 : verticalIndex - 1;
			verticalIndex =
				verticalIndex < 0
					? 0
					: verticalIndex + 1 > dataCount
					? dataCount - 1
					: verticalIndex;

			this.setState({verticalIndex});
		}
		else if (keyCode === keyEnter && verticalIndex > -1) {
			this.addTag(`${dataFiltered[verticalIndex][fieldId]}`);
		}
		else if (keyCode === keyEscape) {
			this.setState({active: false});
		}
		else if (![keyTab, keyEnter].includes(keyCode)) {
			this.showDropList({active: true});
		}
	}

	removeTag(event) {
		const tagIndex = Number(
			event.currentTarget.getAttribute('data-tag-index')
		);
		const {onChangeTags, selectedTagsId} = this.props;

		selectedTagsId.splice(tagIndex, 1);

		if (onChangeTags) {
			onChangeTags(selectedTagsId);
		}
	}

	selectTag(event) {
		this.addTag(event.currentTarget.getAttribute('data-tag-id'));
	}

	setInputRef(inputRef) {
		this.inputRef = inputRef;
	}

	setWrapperRef(wrapperRef) {
		this.wrapperRef = wrapperRef;
	}

	showDropList() {
		this.setState({active: true, highlighted: true});
	}

	toggleDropList() {
		const {active} = this.state;

		if (!active) {
			this.inputRef.focus();
		}
		else {
			this.setState({active: !active});
		}
	}

	render() {
		const {active, highlighted, searchKey, verticalIndex} = this.state;
		const {
			dataFiltered,
			dataFiltered: {length: dataFilteredLength}
		} = this;
		const {fieldId = 'id'} = this.props;
		const selectedTags = this.getSelectedTags();

		const className = `${
			highlighted ? 'is-active' : ''
		} align-items-start form-control form-control-tag-group multi-select-wrapper`;

		const isLast = index => index === dataFilteredLength - 1;

		const tagRender = (tagId, tagIndex, text) => (
			<span
				className="label label-dismissible label-secondary"
				key={tagIndex}
				tabIndex="-1"
			>
				<span className="label-item label-item-expand">{text}</span>

				<span className="label-item label-item-after">
					<button
						aria-label="Close"
						className="close"
						data-tag-id={`${tagId}`}
						data-tag-index={`${tagIndex}`}
						onClick={this.removeTag.bind(this)}
						tabIndex="-1"
						type="button"
					>
						<Icon iconName="times" />
					</button>
				</span>
			</span>
		);

		const placeholder =
			selectedTags.length === 0
				? Liferay.Language.get('select-or-type-an-option')
				: '';

		return (
			<div
				className={className}
				onFocus={this.cancelDropList.bind(this)}
				ref={this.setWrapperRef.bind(this)}
			>
				<div className="col-11 d-flex flex-wrap p-0">
					{selectedTags.map((node, index) =>
						tagRender(node[fieldId], index, node.desc)
					)}

					<input
						className="form-control-inset"
						onChange={this.changeSearch.bind(this)}
						onFocus={this.showDropList.bind(this)}
						onKeyUp={this.onSearch.bind(this)}
						placeholder={placeholder}
						ref={this.setInputRef.bind(this)}
						type="text"
						value={searchKey}
					/>
				</div>

				<div
					className="col-1 drop-icon mt-1 text-right"
					onClick={this.toggleDropList.bind(this)}
				>
					<Icon iconName="caret-double" />
				</div>

				{active && (
					<div
						className="drop-suggestion dropdown-menu"
						tabIndex="-1"
					>
						<ul className="list-unstyled" tabIndex="-1">
							{dataFiltered.map((node, index) => (
								<li
									data-tag-id={`${node[fieldId]}`}
									key={index}
									onClick={this.selectTag.bind(this)}
									tabIndex="-1"
								>
									<a
										className={`dropdown-item ${
											index === verticalIndex
												? 'active'
												: ''
										}`}
										data-senna-off
										href="javascript:;"
										{...(isLast(index)
											? {
													onBlur: this.hideDropList.bind(
														this
													)
											  }
											: {})}
										tabIndex="-1"
									>
										{node.desc}
									</a>
								</li>
							))}

							{dataFilteredLength === 0 && (
								<li className="no-results" tabIndex="-1">
									<a
										className="dropdown-item"
										data-senna-off
										href="javascript:;"
										tabIndex="-1"
									>
										{Liferay.Language.get(
											'no-results-found'
										)}
									</a>
								</li>
							)}
						</ul>
					</div>
				)}
			</div>
		);
	}
}
