@spira_Search @Search @List @team_FARO @priority_5
Feature: Search and Clear Field
	As an Business User, I should be able to search for a user and then reset my search query

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Search and Clear Field
		When I search for "Branda"
		Then I should only see Individuals named "Branda" in the table
		When I search for ""
		Then I should not see Individuals named "Branda" in the table