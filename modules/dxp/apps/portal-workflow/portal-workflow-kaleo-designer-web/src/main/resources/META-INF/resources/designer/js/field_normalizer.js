/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

AUI.add(
	'liferay-kaleo-designer-field-normalizer',
	A => {
		var AArray = A.Array;
		var AObject = A.Object;
		var Lang = A.Lang;

		var KaleoDesignerRemoteServices = Liferay.KaleoDesignerRemoteServices;

		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var COL_TYPES_ASSIGNMENT = [
			'address',
			'receptionType',
			'resourceActions',
			'roleId',
			'roleType',
			'scriptedAssignment',
			'scriptedRecipient',
			'taskAssignees',
			'user',
			'userId'
		];

		var populateRole = function(assignments) {
			KaleoDesignerRemoteServices.getRole(assignments.roleId, data => {
				AArray.each(data, item => {
					if (item) {
						var index = assignments.roleId.indexOf(item.roleId);

						assignments.roleNameAC[index] = item.name;
					}
				});
			});
		};

		var populateUser = function(assignments) {
			if (isValue(assignments.userId)) {
				KaleoDesignerRemoteServices.getUser(
					assignments.userId,
					data => {
						AArray.each(data, item => {
							if (item) {
								var index = assignments.userId.indexOf(
									item.userId
								);

								assignments.emailAddress[index] =
									item.emailAddress;
								assignments.fullName[index] = item.fullName;
								assignments.screenName[index] = item.screenName;
							}
						});
					}
				);
			}
		};

		var _put = function(obj, key, value, index) {
			obj[key] = obj[key] || [];

			if (index === undefined) {
				obj[key].push(value);
			}
			else {
				obj[key][index] = value;
			}
		};

		var FieldNormalizer = {
			normalizeToActions(data) {
				var actions = {};

				data = data || {};

				data.forEach((item1, index1) => {
					A.each(item1, (item2, index2) => {
						if (isValue(item2)) {
							if (index2 === 'script') {
								item2 = Lang.trim(item2);
							}

							_put(actions, index2, item2, index1);
						}
					});
				});

				return actions;
			},

			normalizeToAssignments(data) {
				var assignments = {};

				if (data && data.length) {
					COL_TYPES_ASSIGNMENT.forEach(item1 => {
						var value = data[0][item1];

						if (!isValue(value)) {
							return;
						}

						var assignmentValue = AArray(value);

						assignmentValue.forEach((item2, index2) => {
							if (isObject(item2)) {
								A.each(item2, (item3, index3) => {
									_put(assignments, index3, item3, index2);
								});
							}
							else {
								_put(assignments, item1, item2);
							}
						});

						// Reception type is an assignment attribute but never a type of assignment

						if (
							item1 !== 'receptionType' &&
							AArray.some(assignmentValue, item2 => {
								var valid = isValue(item2);

								if (
									valid &&
									['user', 'roleId', 'roleType'].indexOf(
										item1
									) > -1
								) {
									valid = AArray.some(
										AObject.values(item2),
										isValue
									);
								}

								return valid;
							})
						) {
							assignments.assignmentType = AArray(item1);
						}
					});

					if (assignments.assignmentType == 'roleId') {
						populateRole(assignments);
					}
					else if (assignments.assignmentType == 'user') {
						populateUser(assignments);
					}
				}

				return assignments;
			},

			normalizeToDelays(data) {
				var delays = {};

				data = data || {};

				data.forEach((item1, index1) => {
					A.each(item1, (item2, index2) => {
						if (isValue(item2)) {
							_put(delays, index2, item2, index1);
						}
					});
				});

				return delays;
			},

			normalizeToNotifications(data) {
				var notifications = {};

				data = data || {};

				data.forEach((item1, index1) => {
					A.each(item1, (item2, index2) => {
						if (isValue(item2)) {
							if (index2 === 'recipients') {
								if (item2[0] && item2[0].receptionType) {
									_put(
										notifications,
										'receptionType',
										item2[0].receptionType
									);
								}

								item2 = FieldNormalizer.normalizeToAssignments(
									item2
								);
							}

							_put(notifications, index2, item2, index1);
						}
					});
				});

				return notifications;
			},

			normalizeToTaskTimers(data) {
				var taskTimers = {};

				data = data || {};

				data.forEach((item1, index1) => {
					A.each(item1, (item2, index2) => {
						if (isValue(item2)) {
							if (index2 === 'delay' || index2 === 'recurrence') {
								return;
							}
							else if (index2 === 'timerNotifications') {
								item2 = FieldNormalizer.normalizeToNotifications(
									item2
								);
							}
							else if (index2 === 'timerActions') {
								item2 = FieldNormalizer.normalizeToActions(
									item2
								);
							}
							else if (index2 === 'reassignments') {
								item2 = FieldNormalizer.normalizeToAssignments(
									item2
								);
							}

							_put(taskTimers, index2, item2, index1);
						}
					});

					var delays = item1.delay.concat(item1.recurrence);

					_put(
						taskTimers,
						'delay',
						FieldNormalizer.normalizeToDelays(delays)
					);
				});

				return taskTimers;
			}
		};

		Liferay.KaleoDesignerFieldNormalizer = FieldNormalizer;
	},
	'',
	{
		requires: ['liferay-kaleo-designer-remote-services']
	}
);
