@spira_Accounts @Accounts @List @Search @team_FARO @priority_3
Feature: Assert Account details search results displays correct number
	As a Business User, I should be able to see the correct number of results searched for

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Assert number of results displays correctly
		Given I click "Schneider and Sons" in the table
		And I click the "Details" tab
		When I search for "account"
		Then I should see "3" results returned for "account" in the search results header