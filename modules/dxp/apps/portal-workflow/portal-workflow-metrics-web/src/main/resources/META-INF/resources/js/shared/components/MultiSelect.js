import Icon from './Icon';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
export default class MultiSelect extends React.Component {
	constructor({ data, selectedTags }) {
		super({ data, selectedTags });

		this.state = {
			active: false,
			data,
			searchKey: '',
			selectedTags,
			verticalIndex: -1
		};

		this.addTag = this.addTag.bind(this);
		this.hideDropList = this.hideDropList.bind(this);
		this.onSearch = this.onSearch.bind(this);
		this.removeTag = this.removeTag.bind(this);
		this.showDropList = this.showDropList.bind(this);
	}

	get dataFiltred() {
		const { data, searchKey, selectedTags } = this.state;
		const term = searchKey.toLowerCase().trim();

		return data
			.filter(tag => selectedTags.indexOf(tag) === -1)
			.filter(tag => term === '' || tag.toLowerCase().indexOf(term) > -1);
	}

	addTag(event) {
		const tag = event.currentTarget.getAttribute('data-tag');
		const { selectedTags } = this.state;

		this.setState({
			selectedTags: [...selectedTags, tag]
		});
	}

	hideDropList() {
		this.setState({ active: false });
	}

	onSearch(event) {
		const dataFiltred = this.dataFiltred;
		const {
			keyCode,
			target: { value: searchKey }
		} = event;
		let { selectedTags, verticalIndex } = this.state;

		if ([38, 40].indexOf(keyCode) > -1) {
			const dataCount = dataFiltred.length;

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
			selectedTags = [...selectedTags, dataFiltred[verticalIndex]];
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

	removeTag(event) {
		const tagIndex = Number(event.currentTarget.getAttribute('data-tag-index'));
		const { selectedTags } = this.state;

		selectedTags.splice(tagIndex, 1);
		this.setState({
			selectedTags
		});
	}

	showDropList() {
		this.setState({ active: true });
	}

	render() {
		const { active, selectedTags, verticalIndex } = this.state;

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

		const className = `${
			active ? 'is-active' : ''
		} align-items-start form-control form-control-tag-group multi-select-wrapper`;

		return (
			<div
				className={className}
				onDoubleClick={this.hideDropList}
				onFocus={this.showDropList}
			>
				<div className="col-11 p-0">
					{selectedTags.map((tag, index) => tagRender(tag, index))}
					<input
						className="form-control-inset"
						onKeyUp={this.onSearch}
						type="text"
					/>
				</div>
				<div className="col-1 mt-1">
					<Icon iconName="caret-double" />
				</div>
				{active && (
					<div className="drop-suggestion dropdown-menu" tabIndex="-1">
						<ul className="list-unstyled">
							{this.dataFiltred.map((tag, index) => (
								<li data-tag={`${tag}`} key={index} onClick={this.addTag}>
									<a
										className={`dropdown-item ${
											index === verticalIndex ? 'active' : ''
										}`}
										href={`#${index}`}
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