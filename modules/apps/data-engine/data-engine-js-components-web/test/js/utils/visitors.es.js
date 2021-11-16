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

import {PagesVisitor} from '../../../src/main/resources/META-INF/resources/js/utils/visitors.es';

describe('PagesVisitor', () => {
	let field;
	let nestedField;
	let pages;
	const visitor = new PagesVisitor([]);

	beforeEach(() => {
		nestedField = {fieldName: 'B'};
		field = {fieldName: 'A', nestedFields: [nestedField]};
		pages = [{rows: [{columns: [{fields: [field]}]}]}];
		visitor.setPages(pages);
	});

	describe('mapFields(mapper, merge, includeNestedFields)', () => {
		it('creates new field instances', () => {
			const newPages = visitor.mapFields(() => ({fieldName: 'Z'}));
			const [
				{
					rows: [
						{
							columns: [
								{
									fields: [newField],
								},
							],
						},
					],
				},
			] = newPages;
			expect(newField.fieldName).toEqual('Z');
		});

		it('does not mutate the fields into the original pages', () => {
			visitor.mapFields(() => ({fieldName: 'Z'}));
			expect(field.fieldName).toEqual('A');
		});

		it('passes index as a callback parameters', () => {
			const mapper = jest.fn();
			visitor.mapFields(mapper);
			expect(mapper).toHaveBeenCalledWith(
				field,
				0, // pages index
				0, // rows index
				0, // columns index
				0 // fields index
			);
		});

		it('merges field properties by default', () => {
			const newPages = visitor.mapFields(() => ({fieldName: 'Z'}));
			const [
				{
					rows: [
						{
							columns: [
								{
									fields: [newField],
								},
							],
						},
					],
				},
			] = newPages;
			expect(newField).toEqual({
				fieldName: 'Z',
				nestedFields: [nestedField],
			});
		});

		it('not merges field properties if merge set to false', () => {
			const newPages = visitor.mapFields(() => ({fieldName: 'Z'}), false);
			const [
				{
					rows: [
						{
							columns: [
								{
									fields: [newField],
								},
							],
						},
					],
				},
			] = newPages;
			expect(newField).toEqual({fieldName: 'Z'});
		});

		it('iterates over nested fields', () => {
			const mapper = jest.fn();
			visitor.mapFields(mapper, true, true);
			expect(mapper).toHaveBeenCalledTimes(2);
			expect(mapper).toHaveBeenLastCalledWith(
				nestedField,
				0, // pages index
				0, // rows index
				0, // columns index
				0, // fields index
				field
			);
		});

		it('not iterates over nested fields if merge is false', () => {
			const mapper = jest.fn();
			visitor.mapFields(mapper, false, true);
			expect(mapper).toHaveBeenLastCalledWith(
				field,
				0, // pages index
				0, // rows index
				0, // columns index
				0 // fields index
			);
		});
	});

	describe('mapPages(mapper)', () => {
		it('is able to change pages', () => {
			expect(visitor.mapPages((_page, index) => ({index}))).toEqual([
				{index: 0, rows: [{columns: [{fields: [field]}]}]},
			]);
		});
	});

	describe('mapRows(mapper)', () => {
		it('is able to change rows', () => {
			expect(visitor.mapRows((_row, index) => ({index}))).toEqual([
				{rows: [{columns: [{fields: [field]}], index: 0}]},
			]);
		});
	});

	describe('mapColumns(mapper)', () => {
		it('is able to change columns', () => {
			expect(visitor.mapColumns((_column, index) => ({index}))).toEqual([
				{rows: [{columns: [{fields: [field], index: 0}]}]},
			]);
		});
	});

	describe('visitFields(evaluateField)', () => {
		it('stops on first evaluateField return true', () => {
			const evaluateField = jest.fn(() => true);

			visitor.visitFields(evaluateField);

			expect(evaluateField).toHaveBeenCalledTimes(1);
		});

		it('iterates over nested fields', () => {
			const evaluateField = jest.fn();

			visitor.visitFields(evaluateField);

			expect(evaluateField).toHaveBeenCalledTimes(2);
			expect(evaluateField).toHaveBeenLastCalledWith(nestedField);
		});

		it('iterates over multiple pages', () => {
			const evaluateField = jest.fn();
			const newField = {fieldName: 'C'};
			pages.push({rows: [{columns: [{fields: [newField]}]}]});
			visitor.setPages(pages);

			visitor.visitFields(evaluateField);

			expect(evaluateField).toHaveBeenCalledTimes(3);
			expect(evaluateField).toHaveBeenLastCalledWith(newField);
		});
	});

	describe('findField(condition)', () => {
		it('returns undefined if no condition evaluate to true', () => {
			const fieldFound = visitor.findField(() => false);
			expect(fieldFound).toEqual(undefined);
		});

		it('returns the field on the first condition evaluating to true', () => {
			const fieldFound = visitor.findField(() => true);
			expect(fieldFound).toBe(field);
		});
	});
});
