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

import {deleteResource} from '.';
import fetcher from '../fetcher';
import {APIResponse} from './types';

type Adapter<T = any> = (data: T) => Partial<T>;
type TransformData<T = any> = (data: T) => T;

interface RestContructor<YupModel = any, ObjectModel = any> {
	adapter?: Adapter<YupModel>;
	nestedFields?: string;
	transformData?: TransformData<ObjectModel>;
	uri: string;
}

class Rest<YupModel = any, ObjectModel = any> {
	private batchMinimumThreshold = 10;
	protected adapter: Adapter = (data) => data;
	public fetcher = fetcher;
	public nestedFields: string = '';
	public resource: string = '';
	public transformData: TransformData = (data) => data;
	public uri: string;

	constructor({
		adapter,
		nestedFields,
		transformData,
		uri,
	}: RestContructor<YupModel, ObjectModel>) {
		this.nestedFields = `nestedFields=${nestedFields}`;
		this.uri = uri;
		this.resource = `/${uri}?${this.nestedFields}`;

		if (adapter) {
			this.adapter = adapter;
		}

		if (transformData) {
			this.transformData = transformData;
		}
	}

	protected async beforeCreate(_data: YupModel) {}
	protected async beforeUpdate(_id: number, _data: YupModel) {}

	public async create(data: YupModel): Promise<ObjectModel> {
		await this.beforeCreate(data);

		return fetcher.post(`/${this.uri}`, this.adapter(data));
	}

	public async createBatch(data: YupModel[]): Promise<void> {
		if (data.length >= this.batchMinimumThreshold) {
			return fetcher.post(
				`/${this.uri}/batch`,
				data.map((item) => this.adapter(item))
			);
		}

		await Promise.allSettled(data.map((item) => this.create(item)));
	}

	public getResource(id: number | string) {
		return `/${this.uri}/${id}?${this.nestedFields}`;
	}

	public remove(id: number): Promise<any> | undefined {
		return deleteResource(`/${this.uri}/${id}`);
	}

	public async update(
		id: number,
		data: Partial<YupModel>
	): Promise<ObjectModel> {
		await this.beforeUpdate(id, data as YupModel);

		return fetcher.patch(`/${this.uri}/${id}`, this.adapter(data));
	}

	public transformDataFromList(
		response: APIResponse<ObjectModel>
	): APIResponse<ObjectModel> {
		return {
			...response,
			items: response?.items?.map(this.transformData),
		};
	}
}

export default Rest;
