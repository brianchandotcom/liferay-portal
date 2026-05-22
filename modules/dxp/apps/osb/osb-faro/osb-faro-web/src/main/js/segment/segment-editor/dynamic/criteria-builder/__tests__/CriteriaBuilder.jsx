import CriteriaBuilder from '../index';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';
import {Conjunctions} from '../../utils/constants';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

jest.mock('../CriteriaGroup', () => () => <div />);

jest.unmock('react-dom');

describe('CriteriaBuilder', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaBuilder />
			</DndProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should not render the clear-all button when criteria is empty', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaBuilder
					criteria={{
						conjunctionName: Conjunctions.And,
						criteriaGroupId: 'group',
						items: []
					}}
					onChange={() => {}}
				/>
			</DndProvider>
		);

		expect(
			screen.queryByRole('button', {name: /clear all/i})
		).not.toBeInTheDocument();
	});

	it('should render the clear-all button when criteria has items', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaBuilder
					criteria={{
						conjunctionName: Conjunctions.And,
						criteriaGroupId: 'group',
						items: [{rowId: 'r0', valid: true}]
					}}
					onChange={() => {}}
				/>
			</DndProvider>
		);

		expect(
			screen.getByRole('button', {name: /clear all/i})
		).toBeInTheDocument();
	});
});
