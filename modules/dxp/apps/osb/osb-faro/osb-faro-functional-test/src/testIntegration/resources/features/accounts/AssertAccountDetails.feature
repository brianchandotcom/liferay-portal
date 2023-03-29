@spira_Accounts @Accounts @team_FARO @priority_5
Feature: Assert Account Details
	As an Business User, I should be able to connect a Salesforce Data Source and assert the details of imported Accounts

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
#		* I go to the "Data Source" page
#		* I click the "Add Data Source" button
#		* I click the "Salesforce" button
#		* I type "AssertAccountDetails - ${Random.1}" into the "Name" input field
#		* I complete the Salesforce OAuth authorization
#		* I should see that "AssertAccountDetails - ${Random.1}" was successfully authenticated
#		* I should see that the Salesforce Accounts data is synced

	Scenario: Assert Account Details Tab
		Given I go to the "Accounts" page
		When I search for "Quigley-Larkin"
		And I click "Quigley-Larkin" in the table
		And I click the "Details" tab
		Then I should see the following rows in the Contact Details tab
			| accountName     | Quigley-Larkin               | Name            | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| accountType     | Customer                     | Type            | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| billingCountry  | Uzbekistan                   | BillingCountry  | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| currencyIsoCode | GBP                          | CurrencyIsoCode | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| industry        | Research                     | Industry        | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| phone           | 643.253.6666 x39471          | Phone           | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| website         | https://www.jona-kunze.info  | Website         | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
