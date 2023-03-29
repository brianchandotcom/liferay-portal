@spira_Search @Search @Accounts @List @team_FARO @priority_3
Feature: Search for an Account in the Account List
	As an Business User, I should be able to search for an Account in the Account List by name

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Search for an Account
		Given I go to the "Accounts" page

		# Scenario: Search by an account's full name

		When I search for "Zulauf-Davis"
		Then I should only see Accounts named "Zulauf-Davis" in the table
		When I search for "Moen, Harber and Hintz"
		Then I should only see Accounts named "Moen, Harber and Hintz" in the table

		# Scenario: Search by an account's partial name

		When I search for "Stracke"
		Then I should only see Accounts named "Lesch, Walsh and Stracke" in the table

		# Scenario: Search by an account's lower cased name

		When I search for "sons"
		Then I should only see Accounts named "Schneider and Sons" in the table
	
		# Scenario: Search by an account's upper cased name

		When I search for "LEANNON"
		Then I should only see Accounts named "Lebsack, Leannon and Langworth" in the table