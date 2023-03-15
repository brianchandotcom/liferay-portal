@spira_Segments @Segments @Profile @team_FARO @priority_4
Feature: Segment Membership graph exist and shows members
	As a Business User, I should be able to assert segment membership graph and members

	Background: [Setup] Navigate to Segments page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Assert membership graph and members
		Given I click "engineers" in the table
		When I click the "Membership" tab
		Then I should see "3" total members
		And I click row number "1" in the bar graph table