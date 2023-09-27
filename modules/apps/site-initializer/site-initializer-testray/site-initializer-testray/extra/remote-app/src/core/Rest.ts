/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/i18n';
import fetcher from '~/services/fetcher';
import {APIResponse} from '~/services/rest/types';

import Cache from './Cache';
import SearchBuilder from './SearchBuilder';

type Adapter<T = any> = (data: T) => Partial<T>;
type TransformData<T = any> = (data: T) => T;

export type APIParametersOptions = {
	aggregationTerms?: string;
	customParams?: {[key: string]: unknown};
	fields?: string;
	filter?: string;
	nestedFields?: string;
	nestedFieldsDepth?: number | string;
	page?: number | string;
	pageSize?: number | string;
	sort?: string;
};

const getNestedFieldDepth = (nestedFields?: string) => {
	if (!nestedFields) {
		return 1;
	}

	const nestedFieldsDepthCount = nestedFields
		.split(',')
		.map((item) => item.split('.').length);

	return Math.max(...nestedFieldsDepthCount);
};

interface RestContructor<
	YupModel = any,
	ObjectModel = any,
	NestedObjectOptions = any
> {
	adapter?: Adapter<YupModel>;
	nestedFields?: string;
	nestedObjects?: NestedObjectOptions;
	transformData?: TransformData<ObjectModel>;
	uri: string;
}

class Rest<YupModel = any, ObjectModel = any, NestedObjectOptions = any> {
	private batchMinimumThreshold = 10;
	private cache = Cache.getInstance();
	private nestedFieldsDepth = 1;
	protected adapter: Adapter = (data) => data;
	public fetcher = fetcher;
	public nestedFields: string = '';
	public resource: string = '';
	public transformData: TransformData = (data) => data;
	public uri: string;

	constructor({
		adapter,
		nestedFields = '',
		transformData,
		uri,
	}: RestContructor<YupModel, ObjectModel, NestedObjectOptions>) {
		this.uri = uri;
		this.resource = `/${uri}`;

		if (nestedFields) {
			this.nestedFields = `nestedFields=${nestedFields}`;
			this.nestedFieldsDepth = getNestedFieldDepth(nestedFields);
			this.resource = `/${uri}?${this.nestedFields}&nestedFieldsDepth=${this.nestedFieldsDepth}`;
		}

		if (adapter) {
			this.adapter = adapter;
		}

		if (transformData) {
			this.transformData = transformData;
		}
	}

	static getPageParameter(
		parameters: APIParametersOptions = {},
		baseURL?: string
	) {
		const getBaseSearchParams = (resource?: string) => {
			if (resource && resource.includes('?')) {
				return resource.slice(resource.indexOf('?'));
			}
		};

		const searchParams = new URLSearchParams(getBaseSearchParams(baseURL));

		if (parameters.customParams) {
			parameters = {
				...parameters,
				...parameters.customParams,
			};

			delete parameters.customParams;
		}

		for (const key in parameters) {
			const value = (parameters as any)[key] as
				| string
				| number
				| undefined;

			if (value) {
				searchParams.set(key, value.toString());
			}
		}

		return searchParams.toString();
	}

	protected async beforeCreate(_data: YupModel) {}
	protected async beforeUpdate(_id: number, _data: YupModel) {}
	protected async beforeRemove(_id: number | string) {}

	public async create(data: YupModel): Promise<ObjectModel> {
		await this.beforeCreate(data);

		const response = await fetcher.post(`/${this.uri}`, this.adapter(data));

		if (response && response.name) {
			this.cache.set(`${this.uri}/${response.name}`, response);
		}

		return response;
	}

	public async createIfNotExist(data: YupModel): Promise<ObjectModel> {
		const name = (data as any).name as string;
		const cacheKey = `${this.uri}/${name}`;

		const cachedValue = this.cache.get(cacheKey);

		if (cachedValue) {
			return cachedValue;
		}

		const response = await this.getAll({
			filter: SearchBuilder.eq('name', name),
		});

		const item = response?.items[0];

		if (item) {
			this.cache.set(cacheKey, item);

			return item;
		}

		return this.create(data);
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

	public getAll(
		options: APIParametersOptions = {}
	): Promise<APIResponse<ObjectModel> | undefined> {
		let searchParams = Rest.getPageParameter(options);

		if (searchParams) {
			const operator = this.resource.includes('?') ? '&' : '?';

			searchParams = `${operator}${searchParams}`;
		}

		return this.fetcher(`${this.resource}${searchParams}`);
	}

	public getOne(id: number): Promise<ObjectModel | undefined> {
		return this.fetcher(this.getResource(id));
	}

	public getNestedObject(
		objectName: NestedObjectOptions,
		parentId: number | string
	) {
		return `/${this.uri}/${parentId}/${objectName}`;
	}

	public getResource(id: number | string) {
		return `/${this.uri}/${id}?${this.nestedFields}&nestedFieldsDepth=${this.nestedFieldsDepth}`;
	}

	public async getPagePermission() {
		const response = await this.fetcher<APIResponse<ObjectModel>>(
			`/${this.uri}?pageSize=1&fields=id`
		);

		return !!response?.actions?.create;
	}

	public async remove(id: number | string): Promise<void> {
		await this.beforeRemove(id);

		await fetcher.delete(`/${this.uri}/${id}`);
	}

	public removeResource(id: number | string) {
		if (
			confirm(i18n.translate('are-you-sure-you-want-to-delete-this-item'))
		) {
			return this.remove(id);
		}
	}

	public async update(
		id: number,
		data: Partial<YupModel>
	): Promise<ObjectModel> {
		await this.beforeUpdate(id, data as YupModel);

		return fetcher.patch(`/${this.uri}/${id}`, this.adapter(data));
	}

	public async removeBatch(ids: number[]): Promise<void> {
		await Promise.allSettled(ids.map((id) => this.remove(id)));
	}

	public async updateBatch(
		ids: number[],
		data: Partial<YupModel>[]
	): Promise<PromiseSettledResult<ObjectModel>[]> {
		return Promise.allSettled(
			data.map((item, index) => this.update(ids[index], item))
		);
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
