@spira_Individuals @team_FARO @priority_3
Feature: Selecting a Segment in Individuals
	As a Business User, I should be able to select a segment in individuals

	Background: [Setup] Navigate to Individuals
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page
		* I click the "Known Individuals" tab
		* I search for "Ava Wiza"
		* I click "Ava Wiza" in the table

	Scenario: Select a Segment in Individual
		Given I click the "Segments" tab
		When I click "everybody" in the table
		Then I should see the Segment Profile Cards with their details