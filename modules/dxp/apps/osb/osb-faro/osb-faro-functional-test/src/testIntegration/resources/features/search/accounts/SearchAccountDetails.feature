@spira_Search @Search @Accounts @List @team_FARO @priority_4
Feature: Search an Account's Details
	As an Business User, I should be able to search an Account's Details

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I search for "Quigley-Larkin"
		* I click "Quigley-Larkin" in the table
		* I click the "Details" tab

	Scenario: Search Account Details

		#	No results

		When I search for "some invalid query"
		Then I should see a message that there are no "items" found

		#	Upper case

		When I search for "GBP"
		Then I should see the following rows in the Contact Details tab
			| currencyIsoCode | GBP | CurrencyIsoCode | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
		And I should not see the following rows in the Contact Details tab
			| accountName | Quigley-Larkin | Name | SALESFORCE-DATASOURCE-FARO-EXAMPLE |

		#	Lower case

		When I search for "industry"
		Then I should see the following rows in the Contact Details tab
			| industry | Research | Industry | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
		And I should not see the following rows in the Contact Details tab
			| accountName | Quigley-Larkin | Name | SALESFORCE-DATASOURCE-FARO-EXAMPLE |

		#	Property name

		When I search for "accountType"
		Then I should see the following rows in the Contact Details tab
			| accountType | Customer | Type | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
		And I should not see the following rows in the Contact Details tab
			| accountName | Quigley-Larkin | Name | SALESFORCE-DATASOURCE-FARO-EXAMPLE |

		#	Data source

		When I search for "SALESFORCE-DATASOURCE-FARO-EXAMPLE"
		Then I should see the following rows in the Contact Details tab
			| accountName     | Quigley-Larkin                    | Name            | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| description     | Customizable bottom-line approach | Description     | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| industry        | Research                          | Industry        | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| currencyIsoCode | GBP                               | CurrencyIsoCode | SALESFORCE-DATASOURCE-FARO-EXAMPLE |

		#	Source name

		When I search for "Name"
		Then I should see the following rows in the Contact Details tab
			| accountName | Quigley-Larkin | Name | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
		And I should not see the following rows in the Contact Details tab
			| industry | Research | Industry | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
