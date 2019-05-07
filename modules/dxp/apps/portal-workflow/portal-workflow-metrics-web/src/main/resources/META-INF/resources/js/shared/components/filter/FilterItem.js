import React from 'react';

export default class FilterItem extends React.Component {
	render() {
		const { active, itemKey, name, onChange } = this.props;
		const className = active ? 'active' : '';

		return (
			<li className={`${className} dropdown-item`}>
				<div className="custom-checkbox custom-control">
					<label>
						<input
							checked={!!active}
							className="custom-control-input"
							name={itemKey}
							onChange={onChange}
							type="checkbox"
						/>

						<span className="custom-control-label">
							<span className="custom-control-label-text">{name}</span>
						</span>
					</label>
				</div>
			</li>
		);
	}
}