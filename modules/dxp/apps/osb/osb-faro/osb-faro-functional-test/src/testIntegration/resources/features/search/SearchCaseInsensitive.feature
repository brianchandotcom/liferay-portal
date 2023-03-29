@spira_Search @Search @List @team_FARO @priority_5
Feature: Case Insensitive Search
	As an Business User, I should be able to make case insensitive searches

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Search for an Individual Using a Lowercase Query
		Given I should see the "Individuals" page
		When I search for "abram"
		Then I should only see Individuals named "Abram" in the table

	Scenario: Search for an Individual Using an Uppercase Query
		When I search for "Abram"
		Then I should only see Individuals named "Abram" in the table