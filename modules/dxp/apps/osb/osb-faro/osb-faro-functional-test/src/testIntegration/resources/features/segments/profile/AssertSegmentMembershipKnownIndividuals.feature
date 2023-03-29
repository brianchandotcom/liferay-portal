@spira_Segments @Segments @team_FARO @priority_4
Feature: Assert segment membership list shows all known individuals
	As a Business User, I should be able to assert segment membership all known individuals

	Background: [Setup] Navigate to Segments page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Assert segment membership known individuals
		Given I click "engineers" in the table
		When I click the "Membership" tab
		Then I should see an individual named "Tory Glover" in the table
		And I should see an individual named "Jeni Kertzmann" in the table
		And I should see an individual named "Roy Morissette" in the table