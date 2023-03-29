@spira_Pagination @Pagination @Segments @List @Profile @team_FARO @priority_3
Feature: Paginate Segment Profile in Engagement List
	As a Business User, I should be able to paginate to the second page in engagement list

	Background: [Setup] Navigate to Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "everybody" in the table

	Scenario: Paginate to second page of engagement list
		Given I click the "Membership" tab
		And I click the "Segment Engagement Score" card tab
		When I go to page "2" in the creation modal
		Then I should be on page "2" in the creation modal
		And I should see an individual named "Curt McKenzie" in the table