import RemoteData, {remoteDataFromList} from '../RemoteData';
import {isArray, range} from 'lodash';

describe('RemoteData', () => {
	it('should return a new RemoteData', () => {
		const remoteData = new RemoteData();

		expect(remoteData).toBeTruthy();
	});

	describe('remoteDataFromList', () => {
		it('should return a single RemoteData from an array of RemoteDatas', () => {
			const remoteDatas = range(5).map(() => new RemoteData({data: []}));

			const remoteData = remoteDataFromList(remoteDatas);

			expect(remoteData).toBeInstanceOf(RemoteData);

			remoteData.data.map(item => expect(isArray(item)).toBe(true));
		});

		it('should return a RemoteData with loading as true if any RemoteDatas are loading', () => {
			const remoteDatas = [
				new RemoteData({data: [], loading: true}),
				new RemoteData({data: [], loading: false})
			];

			const remoteData = remoteDataFromList(remoteDatas);

			expect(remoteData.loading).toBe(true);
		});

		it('should return a RemoteData with error as true if any RemoteDatas have an error', () => {
			const remoteDatas = [
				new RemoteData({data: [], error: true}),
				new RemoteData({data: [], error: false})
			];

			const remoteData = remoteDataFromList(remoteDatas);

			expect(remoteData.error).toBe(true);
		});
	});
});
