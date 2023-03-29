import {TimeSpans} from 'shared/util/constants';

export const EVER = 'ever';
export const SINCE = 'since';

export const isKnown = 'is-known';
export const isUnknown = 'is-unknown';

/**
 * Constants for date formatting
 */

export const INPUT_DATE_FORMAT = 'YYYY-MM-DD';
export const INPUT_DATE_TIME_FORMAT = 'YYYY-MM-DDTHH:mmZ';
export const INPUT_DISPLAY_DATE_TIME_FORMAT = 'YYYY-MM-DD HH:mm';

/**
 * Constants for OData query.
 */

export enum Conjunctions {
	And = 'and',
	Or = 'or'
}

export enum CustomFunctionOperators {
	AccountsFilter = 'accounts-filter',
	AccountsFilterByCount = 'accounts-filter-by-count',
	ActivitiesFilter = 'activities-filter',
	ActivitiesFilterByCount = 'activities-filter-by-count',
	EventsFilterByCount = 'events-filter-by-count',
	InterestsFilter = 'interests-filter',
	OrganizationsFilter = 'organizations-filter',
	SessionsFilter = 'sessions-filter'
}

export enum DisplayOnlyOperators {
	IsKnown = 'ne',
	IsUnknown = 'eq'
}

export enum FunctionalOperators {
	Between = 'between',
	Contains = 'contains'
}

export enum NotOperators {
	NotAccountsFilter = 'not-accounts-filter',
	NotAccountsFilterByCount = 'not-accounts-filter-by-count',
	NotActivitiesFilter = 'not-activities-filter',
	NotActivitiesFilterByCount = 'not-activities-filter-by-count',
	NotContains = 'not-contains',
	NotEventsFilterByCount = 'not-events-filter-by-count',
	NotOrganizationsFilter = 'not-organizations-filter',
	NotSessionsFilter = 'not-sessions-filter'
}

export const GROUP = 'GROUP';

export enum RelationalOperators {
	EQ = 'eq',
	GE = 'ge',
	GT = 'gt',
	LE = 'le',
	LT = 'lt',
	NE = 'ne'
}

/**
 * Constants to match property types in the passed in supportedProperties array.
 */

export enum PropertyTypes {
	AccountNumber = 'account-number',
	AccountText = 'account-text',
	Behavior = 'behavior',
	Boolean = 'boolean',
	Date = 'date',
	DateTime = 'date-time',
	Duration = 'duration',
	Event = 'event',
	Interest = 'interest',
	Number = 'number',
	OrganizationBoolean = 'organization-boolean',
	OrganizationDate = 'organization-date',
	OrganizationDateTime = 'organization-date-time',
	OrganizationNumber = 'organization-number',
	OrganizationSelectText = 'organization-select-text',
	OrganizationText = 'organization-text',
	SelectText = 'select-text',
	SessionDateTime = 'session-date-time',
	SessionGeolocation = 'session-geolocation',
	SessionNumber = 'session-number',
	SessionText = 'session-text',
	Text = 'text'
}

/**
 * Constants for CriteriaBuilder component.
 */

export const CUSTOM_FUNCTION_OPERATOR_KEY_MAP = {
	['accounts.filter']: CustomFunctionOperators.AccountsFilter,
	['accounts.filterByCount']: CustomFunctionOperators.AccountsFilterByCount,
	['activities.filter']: CustomFunctionOperators.ActivitiesFilter,
	['activities.filterByCount']:
		CustomFunctionOperators.ActivitiesFilterByCount,
	['events.filterByCount']: CustomFunctionOperators.EventsFilterByCount,
	['interests.filter']: CustomFunctionOperators.InterestsFilter,
	['organizations.filter']: CustomFunctionOperators.OrganizationsFilter,
	['sessions.filter']: CustomFunctionOperators.SessionsFilter
};

export const SUPPORTED_CONJUNCTION_OPTIONS = [
	{
		key: Conjunctions.And,
		label: Liferay.Language.get('and'),
		name: Conjunctions.And
	},
	{
		key: Conjunctions.Or,
		label: Liferay.Language.get('or'),
		name: Conjunctions.Or
	}
];

export const SUPPORTED_OPERATORS_MAP = {
	[PropertyTypes.AccountNumber]: [
		{
			key: CustomFunctionOperators.AccountsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.AccountsFilter
		}
	],
	[PropertyTypes.AccountText]: [
		{
			key: CustomFunctionOperators.AccountsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.AccountsFilter
		}
	],
	[PropertyTypes.Behavior]: [
		{
			key: CustomFunctionOperators.ActivitiesFilterByCount,
			label: Liferay.Language.get('has-fragment'),
			name: CustomFunctionOperators.ActivitiesFilterByCount
		},
		{
			key: NotOperators.NotActivitiesFilterByCount,
			label: Liferay.Language.get('has-not-fragment'),
			name: NotOperators.NotActivitiesFilterByCount
		}
	],
	[PropertyTypes.Boolean]: [
		{
			key: RelationalOperators.EQ,
			label: Liferay.Language.get('is-fragment'),
			name: RelationalOperators.EQ
		}
	],
	[PropertyTypes.Date]: [
		{
			key: RelationalOperators.LT,
			label: Liferay.Language.get('is-before-fragment'),
			name: RelationalOperators.LT
		},
		{
			key: RelationalOperators.EQ,
			label: Liferay.Language.get('is-fragment'),
			name: RelationalOperators.EQ
		},
		{
			key: RelationalOperators.GT,
			label: Liferay.Language.get('is-after-fragment'),
			name: RelationalOperators.GT
		}
	],
	[PropertyTypes.DateTime]: [
		{
			key: RelationalOperators.LT,
			label: Liferay.Language.get('is-before-fragment'),
			name: RelationalOperators.LT
		},
		{
			key: RelationalOperators.EQ,
			label: Liferay.Language.get('is-fragment'),
			name: RelationalOperators.EQ
		},
		{
			key: RelationalOperators.GT,
			label: Liferay.Language.get('is-after-fragment'),
			name: RelationalOperators.GT
		}
	],
	[PropertyTypes.Duration]: [
		{
			key: RelationalOperators.GT,
			label: Liferay.Language.get('greater-than-fragment'),
			name: RelationalOperators.GT
		},
		{
			key: RelationalOperators.LT,
			label: Liferay.Language.get('less-than-fragment'),
			name: RelationalOperators.LT
		}
	],
	[PropertyTypes.Event]: [
		{
			key: CustomFunctionOperators.EventsFilterByCount,
			label: Liferay.Language.get('has-fragment'),
			name: CustomFunctionOperators.EventsFilterByCount
		},
		{
			key: NotOperators.NotActivitiesFilterByCount,
			label: Liferay.Language.get('has-not-fragment'),
			name: NotOperators.NotActivitiesFilterByCount
		}
	],
	[PropertyTypes.Interest]: [
		{
			key: CustomFunctionOperators.InterestsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.InterestsFilter
		}
	],
	[PropertyTypes.Number]: [
		{
			key: RelationalOperators.EQ,
			label: Liferay.Language.get('is-equal-to-fragment'),
			name: RelationalOperators.EQ
		},
		{
			key: RelationalOperators.GT,
			label: Liferay.Language.get('greater-than-fragment'),
			name: RelationalOperators.GT
		},
		{
			key: RelationalOperators.LT,
			label: Liferay.Language.get('less-than-fragment'),
			name: RelationalOperators.LT
		},
		{
			key: RelationalOperators.NE,
			label: Liferay.Language.get('is-not-equal-to-fragment'),
			name: RelationalOperators.NE
		},
		{
			key: isKnown,
			label: Liferay.Language.get('is-known-fragment'),
			name: DisplayOnlyOperators.IsKnown
		},
		{
			key: isUnknown,
			label: Liferay.Language.get('is-unknown-fragment'),
			name: DisplayOnlyOperators.IsUnknown
		}
	],
	[PropertyTypes.OrganizationBoolean]: [
		{
			key: CustomFunctionOperators.OrganizationsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.OrganizationsFilter
		}
	],
	[PropertyTypes.OrganizationDate]: [
		{
			key: CustomFunctionOperators.OrganizationsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.OrganizationsFilter
		}
	],
	[PropertyTypes.OrganizationDateTime]: [
		{
			key: CustomFunctionOperators.OrganizationsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.OrganizationsFilter
		}
	],
	[PropertyTypes.OrganizationNumber]: [
		{
			key: CustomFunctionOperators.OrganizationsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.OrganizationsFilter
		}
	],
	[PropertyTypes.OrganizationSelectText]: [
		{
			key: CustomFunctionOperators.OrganizationsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.OrganizationsFilter
		},
		{
			key: NotOperators.NotOrganizationsFilter,
			label: Liferay.Language.get('is-not-fragment'),
			name: NotOperators.NotOrganizationsFilter
		}
	],
	[PropertyTypes.OrganizationText]: [
		{
			key: CustomFunctionOperators.OrganizationsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.OrganizationsFilter
		}
	],
	[PropertyTypes.SelectText]: [
		{
			key: RelationalOperators.EQ,
			label: Liferay.Language.get('is-fragment'),
			name: RelationalOperators.EQ
		},
		{
			key: RelationalOperators.NE,
			label: Liferay.Language.get('is-not-fragment'),
			name: RelationalOperators.NE
		}
	],
	[PropertyTypes.SessionDateTime]: [
		{
			key: CustomFunctionOperators.SessionsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.SessionsFilter
		}
	],
	[PropertyTypes.SessionGeolocation]: [
		{
			key: CustomFunctionOperators.SessionsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.SessionsFilter
		}
	],
	[PropertyTypes.SessionNumber]: [
		{
			key: CustomFunctionOperators.SessionsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.SessionsFilter
		}
	],
	[PropertyTypes.SessionText]: [
		{
			key: CustomFunctionOperators.SessionsFilter,
			label: Liferay.Language.get('is-fragment'),
			name: CustomFunctionOperators.SessionsFilter
		}
	],
	[PropertyTypes.Text]: [
		{
			key: RelationalOperators.EQ,
			label: Liferay.Language.get('is-fragment'),
			name: RelationalOperators.EQ
		},
		{
			key: RelationalOperators.NE,
			label: Liferay.Language.get('is-not-fragment'),
			name: RelationalOperators.NE
		},
		{
			key: FunctionalOperators.Contains,
			label: Liferay.Language.get('contains-fragment'),
			name: FunctionalOperators.Contains
		},
		{
			key: NotOperators.NotContains,
			label: Liferay.Language.get('does-not-contain-fragment'),
			name: NotOperators.NotContains
		},
		{
			key: isKnown,
			label: Liferay.Language.get('is-known-fragment'),
			name: DisplayOnlyOperators.IsKnown
		},
		{
			key: isUnknown,
			label: Liferay.Language.get('is-unknown-fragment'),
			name: DisplayOnlyOperators.IsUnknown
		}
	]
};

export const SUPPORTED_PROPERTY_TYPES_MAP = {
	[PropertyTypes.AccountNumber]: [CustomFunctionOperators.AccountsFilter],
	[PropertyTypes.AccountText]: [CustomFunctionOperators.AccountsFilter],
	[PropertyTypes.Behavior]: [
		CustomFunctionOperators.ActivitiesFilterByCount,
		NotOperators.NotActivitiesFilterByCount
	],
	[PropertyTypes.Boolean]: [RelationalOperators.EQ],
	[PropertyTypes.Date]: [
		RelationalOperators.EQ,
		RelationalOperators.GE,
		RelationalOperators.GT,
		RelationalOperators.LE,
		RelationalOperators.LT,
		RelationalOperators.NE
	],
	[PropertyTypes.DateTime]: [
		RelationalOperators.EQ,
		RelationalOperators.GE,
		RelationalOperators.GT,
		RelationalOperators.LE,
		RelationalOperators.LT,
		RelationalOperators.NE
	],
	[PropertyTypes.Duration]: [RelationalOperators.GT, RelationalOperators.LT],
	[PropertyTypes.Event]: [
		CustomFunctionOperators.EventsFilterByCount,
		NotOperators.NotActivitiesFilterByCount
	],
	[PropertyTypes.Interest]: [CustomFunctionOperators.InterestsFilter],
	[PropertyTypes.Number]: [
		RelationalOperators.EQ,
		RelationalOperators.GE,
		RelationalOperators.GT,
		RelationalOperators.LE,
		RelationalOperators.LT,
		RelationalOperators.NE
	],
	[PropertyTypes.SessionDateTime]: [PropertyTypes.SessionDateTime],
	[PropertyTypes.SessionNumber]: [CustomFunctionOperators.SessionsFilter],
	[PropertyTypes.SessionText]: [CustomFunctionOperators.SessionsFilter],
	[PropertyTypes.Text]: [
		RelationalOperators.EQ,
		RelationalOperators.NE,
		FunctionalOperators.Contains,
		NotOperators.NotContains,
		DisplayOnlyOperators.IsKnown,
		DisplayOnlyOperators.IsUnknown
	]
};

/**
 * Values for criteria row inputs.
 */

export const STRING_OPTIONS = [
	FunctionalOperators.Contains,
	NotOperators.NotContains,
	RelationalOperators.EQ,
	RelationalOperators.NE
];

export const STRING_OPERATOR_LABELS_MAP = {
	[FunctionalOperators.Contains]: Liferay.Language.get('contains-fragment'),
	[NotOperators.NotContains]: Liferay.Language.get('not-contains-fragment'),
	[RelationalOperators.EQ]: Liferay.Language.get('is-fragment'),
	[RelationalOperators.NE]: Liferay.Language.get('is-not-fragment')
};

export const BOOLEAN_OPTIONS = [
	{
		label: Liferay.Language.get('true'),
		value: 'true'
	},
	{
		label: Liferay.Language.get('false'),
		value: 'false'
	}
];

export const INTEREST_BOOLEAN_OPTIONS = [
	{
		label: Liferay.Language.get('is-fragment'),
		value: 'true'
	},
	{
		label: Liferay.Language.get('is-not-fragment'),
		value: 'false'
	}
];

export const OCCURENCE_OPTIONS = [
	{
		key: RelationalOperators.GE,
		label: Liferay.Language.get('at-least-fragment'),
		value: RelationalOperators.GE
	},
	{
		key: RelationalOperators.LE,
		label: Liferay.Language.get('at-most-fragment'),
		value: RelationalOperators.LE
	}
];

export const GEOLOCATION_OPTIONS = [
	{
		label: Liferay.Language.get('was-fragment'),
		value: RelationalOperators.EQ
	},
	{
		label: Liferay.Language.get('was-not-fragment'),
		value: RelationalOperators.NE
	},
	{
		label: Liferay.Language.get('contained-fragment'),
		value: FunctionalOperators.Contains
	},
	{
		label: Liferay.Language.get('did-not-contain-fragment'),
		value: NotOperators.NotContains
	}
];

export const TIME_CONJUNCTION_OPTIONS = [
	{
		label: Liferay.Language.get('since-fragment'),
		value: SINCE
	},
	{
		label: Liferay.Language.get('after-fragment'),
		value: RelationalOperators.GT
	},
	{
		label: Liferay.Language.get('before-fragment'),
		value: RelationalOperators.LT
	},
	{
		label: Liferay.Language.get('between-fragment'),
		value: FunctionalOperators.Between
	},
	{
		label: Liferay.Language.get('ever-fragment'),
		value: EVER
	},
	{
		label: Liferay.Language.get('on-fragment'),
		value: RelationalOperators.EQ
	}
];

export const ACTIVITY_KEY = 'activityKey';
export const EVENT_KEY = 'eventDefinitionId';

export const TIME_PERIOD_OPTIONS = [
	{
		label: Liferay.Language.get('last-24-hours'),
		value: TimeSpans.Last24Hours
	},
	{
		label: Liferay.Language.get('yesterday'),
		value: TimeSpans.Yesterday
	},
	{
		label: Liferay.Language.get('last-seven-days'),
		value: TimeSpans.Last7Days
	},
	{
		label: Liferay.Language.get('last-28-days'),
		value: TimeSpans.Last28Days
	},
	{
		label: Liferay.Language.get('last-30-days'),
		value: TimeSpans.Last30Days
	},
	{
		label: Liferay.Language.get('last-90-days'),
		value: TimeSpans.Last90Days
	}
];

export {TimeSpans};
