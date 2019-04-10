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

		const { data, selectedTags } = props;

		this.state = {
			active: false,
			data,
			searchKey: '',
			selectedTags,
			verticalIndex: -1
		};
	}

	componentDidMount() {
		document.addEventListener('mousedown', this.handleClickOutside);
	}

	componentWillUnmount() {
		document.removeEventListener('mousedown', this.handleClickOutside);
	}

	@autobind
	addTag(event) {
		const tag = event.currentTarget.getAttribute('data-tag');
		const { selectedTags } = this.state;

		this.setState({
			selectedTags: [...selectedTags, tag]
		});
	}

	@autobind
	cancelAllDropList() {
		const event = new Event('mousedown', { bubbles: true });

		this.wrapperRef.dispatchEvent(event);
	}

	get dataFiltered() {
		const { data, searchKey, selectedTags } = this.state;
		const term = searchKey.toLowerCase().trim();

		return data
			.filter(tag => selectedTags.indexOf(tag) === -1)
			.filter(tag => term === '' || tag.toLowerCase().indexOf(term) > -1);
	}

	@autobind
	handleClickOutside(event) {
		if (
			this.state.active &&
			this.dataFiltered.length &&
			this.wrapperRef &&
			!this.wrapperRef.contains(event.target)
		) {
			this.hideDropList();
		}
	}

	@autobind
	hideDropList() {
		this.setState({ active: false });
	}

	@autobind
	onSearch(event) {
		const dataFiltered = this.dataFiltered;
		const {
			keyCode,
			target: { value: searchKey }
		} = event;
		let { selectedTags, verticalIndex } = this.state;

		if ([38, 40].indexOf(keyCode) > -1) {
			const dataCount = dataFiltered.length;

			verticalIndex = keyCode === 40 ? verticalIndex + 1 : verticalIndex - 1;
			verticalIndex =
				verticalIndex < 0
					? 0
					: verticalIndex + 1 > dataCount
						? dataCount - 1
						: verticalIndex;

			this.setState({ verticalIndex });
		}
		else if (keyCode === 13 && verticalIndex > -1) {
			selectedTags = [...selectedTags, dataFiltered[verticalIndex]];
			this.setState({
				searchKey: '',
				selectedTags,
				verticalIndex: -1
			});
		}
		else {
			this.setState({
				searchKey,
				verticalIndex: -1
			});
		}
	}

	@autobind
	removeTag(event) {
		const tagIndex = Number(event.currentTarget.getAttribute('data-tag-index'));
		const { selectedTags } = this.state;

		selectedTags.splice(tagIndex, 1);
		this.setState({
			selectedTags
		});
	}

	@autobind
	setWrapperRef(wrapperRef) {
		this.wrapperRef = wrapperRef;
	}

	@autobind
	showDropList() {
		this.setState({ active: true });
	}

	@autobind
	toggleDropList() {
		this.setState({ active: !this.state.active });
	}

	render() {
		const { active, selectedTags, verticalIndex } = this.state;
		const {
			dataFiltered,
			dataFiltered: { length: dataFilteredLength }
		} = this;

		const className = `${
			active ? 'is-active' : ''
		} align-items-start form-control form-control-tag-group multi-select-wrapper`;

		const isLast = index => index === dataFilteredLength - 1;

		const tagRender = (text, tagIndex) => (
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
						data-tag-index={`${tagIndex}`}
						onClick={this.removeTag}
						type="button"
					>
						<Icon iconName="times" />
					</button>
				</span>
			</span>
		);

		return (
			<div
				className={className}
				onFocus={this.cancelAllDropList}
				ref={this.setWrapperRef}
			>
				<div className="col-11 p-0 d-flex flex-wrap">
					{selectedTags.map((tag, index) => tagRender(tag, index))}

					<input
						className="form-control-inset"
						onFocus={this.showDropList}
						onKeyUp={this.onSearch}
						type="text"
					/>
				</div>

				<div
					className="col-1 mt-1 text-right"
					onClick={this.toggleDropList}
					style={{ paddingRight: '0px' }}
				>
					<Icon iconName="caret-double" />
				</div>

				{active && dataFilteredLength > 0 && (
					<div className="drop-suggestion dropdown-menu" tabIndex="-1">
						<ul className="list-unstyled">
							{dataFiltered.map((tag, index) => (
								<li data-tag={`${tag}`} key={index} onClick={this.addTag}>
									<a
										className={`dropdown-item ${
											index === verticalIndex ? 'active' : ''
										}`}
										data-senna-off
										href="javascript:;"
										{...(isLast(index) ? { onBlur: this.hideDropList } : {})}
									>
										{tag}
									</a>
								</li>
							))}
						</ul>
					</div>
				)}
			</div>
		);
	}
}