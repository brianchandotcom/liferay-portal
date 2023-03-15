@spira_Accounts @Accounts @Profile @team_FARO @priority_3
Feature: Total number of associated segments are displayed
	As a Business User, I should be able to see total number of associated segments

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: View number of segments
		Given I click "Schneider and Sons" in the table
		When I click the "Segments" tab
		Then I should see "2" rows in the table
		And I should see "2" total associated segments