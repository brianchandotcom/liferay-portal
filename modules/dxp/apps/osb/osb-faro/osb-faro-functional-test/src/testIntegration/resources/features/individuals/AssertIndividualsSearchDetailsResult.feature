@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Assert Individuals details search result displays correct number
	As a Business User, I should be able to see the correct number of results searched for

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Assert number of results displays correctly
		Given I click the "Known Individuals" tab
		And I search for "Ava Wiza"
		And I click "Ava Wiza" in the table
		And I click the "Details" tab
		When I search for "name"
		Then I should see "4" results returned for "name" in the search results header