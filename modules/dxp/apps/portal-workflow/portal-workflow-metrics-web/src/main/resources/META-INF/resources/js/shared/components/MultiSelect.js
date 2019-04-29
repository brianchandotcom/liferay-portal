import autobind from 'autobind-decorator';
import Icon from './Icon';
import React from 'react';

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
		document.addEventListener('mousedown', this.handleClickOutside);
	}

	componentWillUnmount() {
		document.removeEventListener('mousedown', this.handleClickOutside);
	}

	addTag(tagId) {
		const { onChangeTags, selectedTagsId } = this.props;

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

	@autobind
	cancelDropList() {
		const event = new Event('mousedown', { bubbles: true });

		this.wrapperRef.dispatchEvent(event);
	}

	@autobind
	changeSearch(event) {
		const {
			target: { value: searchKey }
		} = event;

		this.setState({
			searchKey,
			verticalIndex: -1
		});
	}

	get dataFiltered() {
		const { data, fieldId = 'id', selectedTagsId } = this.props;
		const { searchKey } = this.state;

		const term = searchKey.toLowerCase().trim();

		return data
			.filter(node => !selectedTagsId.includes(`${node[fieldId]}`))
			.filter(
				({ desc }) => term === '' || desc.toLowerCase().indexOf(term) > -1
			);
	}

	getSelectedTags() {
		const { data, fieldId = 'id', selectedTagsId } = this.props;

		return data.filter(node => selectedTagsId.includes(`${node[fieldId]}`));
	}

	@autobind
	handleClickOutside(event) {
		if (this.wrapperRef && !this.wrapperRef.contains(event.target)) {
			this.hideDropList();
		}
	}

	@autobind
	hideDropList() {
		this.setState({ active: false, highlighted: false });
	}

	@autobind
	onSearch(event) {
		const dataFiltered = this.dataFiltered;
		const { fieldId = 'id' } = this.props;
		const keyArrowDown = 38;
		const keyArrowUp = 40;
		const { keyCode } = event;
		const keyEnter = 13;
		const keyEscape = 27;
		const keyTab = 9;
		let { verticalIndex } = this.state;

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

			this.setState({ verticalIndex });
		}
		else if (keyCode === keyEnter && verticalIndex > -1) {
			this.addTag(`${dataFiltered[verticalIndex][fieldId]}`);
		}
		else if (keyCode === keyEscape) {
			this.setState({ active: false });
		}
		else if (![keyTab, keyEnter].includes(keyCode)) {
			this.showDropList({ active: true });
		}
	}

	@autobind
	removeTag(event) {
		const tagIndex = Number(event.currentTarget.getAttribute('data-tag-index'));
		const { onChangeTags, selectedTagsId } = this.props;

		selectedTagsId.splice(tagIndex, 1);

		if (onChangeTags) {
			onChangeTags(selectedTagsId);
		}
	}

	@autobind
	selectTag(event) {
		this.addTag(event.currentTarget.getAttribute('data-tag-id'));
	}

	@autobind
	setInputRef(inputRef) {
		this.inputRef = inputRef;
	}

	@autobind
	setWrapperRef(wrapperRef) {
		this.wrapperRef = wrapperRef;
	}

	@autobind
	showDropList() {
		this.setState({ active: true, highlighted: true });
	}

	@autobind
	toggleDropList() {
		const { active } = this.state;

		if (!active) {
			this.inputRef.focus();
		}
		else {
			this.setState({ active: !active });
		}
	}

	render() {
		const { active, highlighted, searchKey, verticalIndex } = this.state;
		const {
			dataFiltered,
			dataFiltered: { length: dataFilteredLength }
		} = this;
		const { fieldId = 'id' } = this.props;
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
						onClick={this.removeTag}
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
				onFocus={this.cancelDropList}
				ref={this.setWrapperRef}
			>
				<div className="col-11 p-0 d-flex flex-wrap">
					{selectedTags.map((node, index) =>
						tagRender(node[fieldId], index, node.desc)
					)}

					<input
						className="form-control-inset"
						onChange={this.changeSearch}
						onFocus={this.showDropList}
						onKeyUp={this.onSearch}
						placeholder={placeholder}
						ref={this.setInputRef}
						type="text"
						value={searchKey}
					/>
				</div>

				<div
					className="col-1 mt-1 text-right"
					onClick={this.toggleDropList}
					style={{ paddingRight: '0px' }}
				>
					<Icon iconName="caret-double" />
				</div>

				{active && (
					<div className="drop-suggestion dropdown-menu" tabIndex="-1">
						<ul className="list-unstyled" tabIndex="-1">
							{dataFiltered.map((node, index) => (
								<li
									data-tag-id={`${node[fieldId]}`}
									key={index}
									onClick={this.selectTag}
									tabIndex="-1"
								>
									<a
										className={`dropdown-item ${
											index === verticalIndex ? 'active' : ''
										}`}
										data-senna-off
										href="javascript:;"
										{...(isLast(index) ? { onBlur: this.hideDropList } : {})}
										tabIndex="-1"
									>
										{node.desc}
									</a>
								</li>
							))}

							{dataFilteredLength === 0 && (
								<li tabIndex="-1">
									<a
										className="dropdown-item"
										data-senna-off
										href="javascript:;"
										tabIndex="-1"
									>
										{Liferay.Language.get('no-results-found')}
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