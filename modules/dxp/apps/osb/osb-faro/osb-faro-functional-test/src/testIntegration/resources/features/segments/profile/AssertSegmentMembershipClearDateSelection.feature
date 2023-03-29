@spira_Segments @Segments @Profile @team_FARO @priority_3
Feature: Selected segment membership point can clear date selection
	As a Business User, I should be able to clear date selection from segment membership

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "engineers" in the table

	Scenario: Select clear date selection
		Given I click the "Membership" tab
		And I click row number "1" in the bar graph table
		When I click the "Clear Date Selection" button
		Then I should see an element exists in the table