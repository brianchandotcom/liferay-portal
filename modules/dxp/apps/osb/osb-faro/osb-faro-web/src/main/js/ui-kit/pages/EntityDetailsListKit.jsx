import * as data from 'test/data';
import EntityDetailsList from 'contacts/components/EntityDetailsList';
import React from 'react';
import {fromJS} from 'immutable';

export default class EntityDetailsListKit extends React.Component {
	render() {
		const mockNullField = data.mockNullFieldMapping(1, {
			context: 'organization'
		});

		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<EntityDetailsList
					demographicsIMap={fromJS(
						data.mockAccountDetails({nullField: [mockNullField]})
					)}
					groupId='23'
					title='Entity Details List'
				/>
			</div>
		);
	}
}
