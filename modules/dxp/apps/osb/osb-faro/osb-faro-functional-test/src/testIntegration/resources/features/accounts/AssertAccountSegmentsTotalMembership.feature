@spira_Accounts @Accounts @Profile @team_FARO @priority_3
Feature: Total Membership of segments is shown
	As a Business User, I should be able to see total membership of segments

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Total Membership of segments
		Given I click "Schneider and Sons" in the table
		When I click the "Segments" tab
		Then I should see a Segment named "engineers" with "3" items
		And I should see a Segment named "everybody" with "98" items