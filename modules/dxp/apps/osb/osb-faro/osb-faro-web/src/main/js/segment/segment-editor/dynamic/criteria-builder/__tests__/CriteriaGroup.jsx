import * as data from 'test/data';
import CriteriaGroup from '../CriteriaGroup';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

jest.mock('../Conjunction', () => () => <div />);
jest.mock('../CriteriaRow', () => () => <div />);
jest.mock('../DropZone', () => () => <div />);

jest.unmock('react-dom');

const LIMIT_MESSAGE = 'Maximum of 5 sequential criteria has been reached.';
const EXCEEDED_MESSAGE =
	'Maximum of 5 sequential criteria has been exceeded. Remove some criteria to save.';

describe('CriteriaGroup', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaGroup />
			</DndProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render the limit alert when sequential and items.length >= 5', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaGroup
					criteria={data.mockNewCriteria(5, {valid: true})}
					criteriaGroupId='group-1'
					onChange={() => {}}
					onMove={() => {}}
					sequential
				/>
			</DndProvider>
		);

		expect(screen.getByText(LIMIT_MESSAGE)).toBeInTheDocument();
	});

	it('should not render the limit alert when sequential and items.length < 5', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaGroup
					criteria={data.mockNewCriteria(4, {valid: true})}
					criteriaGroupId='group-1'
					onChange={() => {}}
					onMove={() => {}}
					sequential
				/>
			</DndProvider>
		);

		expect(screen.queryByText(LIMIT_MESSAGE)).not.toBeInTheDocument();
	});

	it('should not render the limit alert when not sequential even with 5 items', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaGroup
					criteria={data.mockNewCriteria(5, {valid: true})}
					criteriaGroupId='group-1'
					onChange={() => {}}
					onMove={() => {}}
					sequential={false}
				/>
			</DndProvider>
		);

		expect(screen.queryByText(LIMIT_MESSAGE)).not.toBeInTheDocument();
	});

	it('should render the exceeded alert when sequential and items.length > 5', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaGroup
					criteria={data.mockNewCriteria(6, {valid: true})}
					criteriaGroupId='group-1'
					onChange={() => {}}
					onMove={() => {}}
					sequential
				/>
			</DndProvider>
		);

		expect(screen.getByText(EXCEEDED_MESSAGE)).toBeInTheDocument();
		expect(screen.queryByText(LIMIT_MESSAGE)).not.toBeInTheDocument();
	});
});
